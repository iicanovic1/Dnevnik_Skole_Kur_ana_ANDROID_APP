 package com.example.dnevnikskolekur_ana.other

import kotlinx.coroutines.flow.*
           // rezultat  // zahtjev -response (List<Student>)
inline fun <ResultType, RequestType> networkBoundResource( // vraća flow
    crossinline query: () -> Flow<ResultType>,// REZULTAT // dobavljanje iz loklane baze
    crossinline fetch: suspend () -> RequestType,// ZAHTJEV // dobavljanje sa API - ja
    crossinline saveFetchResult: suspend (RequestType) -> Unit, // ZAHTJEV // spašavanje što smo dobili sa APi-ja u bazu
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit }, // pomaže šta da radimo kad ne uspije fetch sa API-ja
    crossinline shouldFetch: (ResultType) -> Boolean = {true} // REZULTAT // prima iz Baze i određeuje hoćemo li to ili ćemo fetch sa API
) = flow {
    emit(Resource.loading(null))  // kada se pozove ova funkcija Live Data bude trigerovan
    val data = query().first() // dobavljanje podataka iz baze - flow liste studenata

    val flow = if(shouldFetch(data)){ // hoćemo fetch sa servera da pokušamo
        emit(Resource.loading(data))  // možemo trenutno pokazati iz baze što imamo sad za sad

        try {
            val fetcedResult = fetch() // Fetch sa API
            //saveFetchResult(fetcedResult)  // spašavanje u bazu
            query().map { Resource.success(it)} // pozivanje iz baze i smještanje u resurs
        }catch (t  : Throwable){
            onFetchFailed(t)
            query().map {
                Resource.error("Nije moguće kontaktirati server!", it)
            } // opet će uzeti iz baze ali ono što je već prije  bilo bez update sa API -ja
        }
    }else{
        query().map {Resource.success (it)} // svakako nećeš sa API , uzmi iz baze samo
    }
    emitAll(flow)
}