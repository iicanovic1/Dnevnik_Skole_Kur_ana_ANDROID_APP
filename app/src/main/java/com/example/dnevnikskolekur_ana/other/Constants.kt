package com.example.dnevnikskolekur_ana.other

import com.example.dnevnikskolekur_ana.data.local.entities.AnswerType
import com.example.dnevnikskolekur_ana.data.local.entities.Section
import com.example.dnevnikskolekur_ana.data.local.entities.Chapter

object Constants {
    val IGNORE_AUTH_URLS= listOf("/login","/register") // lista urlova koji se ne presreću za zaglavlje o autorizaciji

    const val DATABASE_NAME = "students_db"

    const val BASE_URL = "https://192.168.1.7:8002" // Retrofit spajanje na server

    const val ENCRYPTED_SHARED_PREF_NAME = "en_shared_pref"
    const val KEY_LOGGED_IN_EMAIL = "KEY_LOGGED_IN_EMAIL"
    const val KEY_PASSWORD = "KEY_PASSWORD"
    const val NO_EMAIL = "NO_EMAIL"
    const val NO_PASSWORD = "NO_PASSWORD"

    const val DEFAULT_STUDENT_COLOR = "FFA500"

    val MARKS = List(5,{1+it})

    val Chapter_NULL = Chapter("Odaberite poglavlje",null)
    val AL_FATIHA = Chapter("1.AL-FATIHA",7)
    val AL_BEKARA = Chapter("2.AL-BEKARA",286)
    val ALI_IMRAN = Chapter("3.ALI-IMRAN",200)
    val AN_NISA = Chapter("4.AN-NISA",176)
    val AL_MAIDA = Chapter("5.AL-MAIDA",120)
    val AL_ANAM = Chapter("6.AL-ANAM",165)
    val AL_ARAF = Chapter("7.AL-ARAF",206)
    val AL_ENFAL = Chapter("8.AL-ENFAL",75)
    val AT_TEVBA = Chapter("9.AT-TEVBA",129)
    val JUNUS = Chapter("10.JUNUS",109)
    val HUD = Chapter("11.HUD",123)
    val JUSUF = Chapter("12.JUSUF",111)
    val AR_RAD = Chapter("13.AR-RAD",43)
    val IBRAHIM = Chapter("14.IBRAHIM",52)
    val AL_HIJR = Chapter("15.AL-HIDŽR",99)
    val AN_NAHL = Chapter("16.AN-NAHL",128)
    val AL_ISRA = Chapter("17.AL-ISRA",111)
    val AL_KEHF = Chapter("18.AL-KEHF",110)
    val MERJEM = Chapter("19.MERJEM",98)
    val TAHA = Chapter("20.TAHA",135)
    val AL_ENBIJA = Chapter("21.AL-ENBIJA",112)
    val AL_HADZDZ = Chapter("22.AL-HADŽDŽ",78)
    val AL_MUMINUN = Chapter("23.AL-MU'MINUN",118)
    val AN_NUR = Chapter("24.AN-NUR",64)
    val AL_FURKAN = Chapter("25.AL-FURKAN",64)
    val AS_SUARA = Chapter("26.AL-FURKAN",77)
    val AN_NAML = Chapter("27.AN-NAML",93)
    val AL_KASAS = Chapter("28.AL-KASAS",88)
    val AL_ANKEBUT = Chapter("29.AL-ANKEBUT",69)
    val AR_RUM = Chapter("30.AR-RUM",60)
    val LUKMAN = Chapter("31.LUKMAN",34)
    val AS_SEDZDE = Chapter("32.AS-SEDŽDE",30)
    val AL_AHZAB = Chapter("33.AL-AHZAB",73)
    val SEBE = Chapter("34.SEBE",54)
    val FATIR = Chapter("35.FATIR",45)
    val YA_SIN = Chapter("36.JA-SIN",83)
    val AS_SAFAT = Chapter("37.AS-SAFAT",182)
    val SAD = Chapter("38.SAD",88)
    val AZ_ZUMER = Chapter("39.AZ-ZUMER",75)
    val MUMIN = Chapter("40.MU'MIN",85)
    val FUSSILET = Chapter("41.FUSSILET",54)
    val AS_SURA = Chapter("42.AS-ŠURA",53)
    val AZ_ZUHRUF = Chapter("43.AZ-ZUHRUF",89)
    val AD_DUHAN = Chapter("44.AD-DUHAN",59)
    val AL_DZASIJE = Chapter("45.AL-DŽASIJE",37)
    val AL_AHKAF = Chapter("46.AL-AHKAF",35)
    val MUHAMMAD = Chapter("47.MUHAMMED",38)
    val AL_FATH = Chapter("48.AL-FETH",29)
    val AL_HUDZURAT = Chapter("49.AL-HUDŽURAT",18)
    val QAF = Chapter("50.KAF",45)
    val AZ_ZARIJAT = Chapter("51.AZ-ZARIJAT",60)
    val AT_TUR = Chapter("52.AT-TUR",49)
    val AN_NADZM = Chapter("53.AN-NEDŽM",62)
    val AL_KAMER = Chapter("54.AL-KAMER",55)
    val AR_RAHMAN = Chapter("55.AR-RAHMAN",78)
    val AL_VAKIA = Chapter("56.AL-VAKIA",96)
    val AL_HADID = Chapter("57.AL-HADID",29)
    val AL_MUDZADELE = Chapter("58.AL-MUDŽADELE",22)
    val AL_HASR = Chapter("59.AL-HAŠR",24)
    val AL_MUMTEHINE = Chapter("60.AL-MUMTEHINE",13)
    val AS_SAFF = Chapter("61.AS-SAFF",14)
    val AL_DZUMUA = Chapter("62.AL-DŽUMUA",11)
    val AL_MUNAFIKUN = Chapter("63.AL-MUNAFIKUN",11)
    val AT_TEGABUN = Chapter("64.AT-TEGABUN",18)
    val AT_TALAK = Chapter("65.AT-TALAK",12)
    val AT_TAHRIM = Chapter("66.AT-TAHRIM",12)
    val AL_MULK = Chapter("67.AL-MULK",30)
    val AL_KALEM = Chapter("68.AL-KALEM",52)
    val AL_MEARIDZ = Chapter("70.AL-MEARIDŽ",44)
    val NUH = Chapter("71.NUH",28)
    val AL_DZINN = Chapter("72.AL-DŽINN",28)
    val AL_MUZZEMMIL = Chapter("73.AL-MUZZEMMIL",20)
    val AL_MUDDESSIR = Chapter("74.AL-MUDDESSIR",56)
    val AL_KIJAME = Chapter("75.AL-KIJAME",40)
    val AL_INSAN = Chapter("76.AL-INSAN",31)
    val AL_MURSELAT = Chapter("77.AL-MURSELAT",50)
    val AN_NEBE = Chapter("78.AN-NEBE",40)
    val AN_NAZIAT = Chapter("79.AN-NAZIAT",46)
    val ABESE = Chapter("80.ABESE",42)
    val AT_TEKVIR = Chapter("81.AT-TEKVIR",29)
    val AL_INFITAR = Chapter("82.AL-INFITAR",19)
    val AL_MUTAFFIFIN = Chapter("83.AL-MUTAFFIFIN",36)
    val AL_INSIKAK = Chapter("84.AL-INŠIKAK",25)
    val AL_BURUDZ = Chapter("85.AL-BURUDŽ",22)
    val AT_TARIK = Chapter("86.AT-TARIK",17)
    val AL_ELA = Chapter("87.AL-E'LA",19)
    val AL_GASIJE = Chapter("88.AL-GAŠIJE",26)
    val AL_FEDZR = Chapter("89.AL-FEDŽR",30)
    val AL_BELED = Chapter("90.AL-BELED",20)
    val AS_SEMS = Chapter("91.AŠ-ŠEMS",15)
    val AL_LEJL = Chapter("92.AL-LEJL",21)
    val AD_DUHA = Chapter("93.AD-DUHA",11)
    val AL_INSIRAH = Chapter("94.AL-INŠIRAH",8)
    val AT_TIN = Chapter("95.AT-TIN",8)
    val AL_ALEK = Chapter("96.AL-'ALEK'",19)
    val AL_KADR = Chapter("97.AL-KADR",5)
    val AL_BEJJINE = Chapter("98.AL-BEJJINE",8)
    val AZ_ZELZELE = Chapter("99.AZ-ZELZELE",8)
    val AL_ADIJAT = Chapter("100.AL-'ADIJAT",11)
    val AL_KARIA = Chapter("101.AL-KARIA",11)
    val AT_TEKASUR = Chapter("102.AT-TEKASUR",8)
    val AL_ASR = Chapter("103.AL-ASR",3)
    val AL_HUMEZE = Chapter("104.AL-HUMEZE",9)
    val AL_FIL = Chapter("105.AL-FIL",5)
    val KUREJS = Chapter("106.KUREJŠ",4)
    val AL_MAUN = Chapter("107.AL-MAUN",7)
    val AL_KEVSER = Chapter("108.AL-KEVSER",3)
    val AL_KAFIRUN = Chapter("109.AL-KAFIRUN",6)
    val AN_NASR = Chapter("110.AN-NASR",3)
    val AL_LEHEB= Chapter("111.AL-LEHEB",5)
    val AL_IHLAS= Chapter("112.AL-IHLAS",4)
    val AL_FELEK= Chapter("113.AL-FELEK",5)
    val AN_NAS= Chapter("114.AN-NAS",6)



    val Section_NULL = Section("Odaberite cjelinu",0, emptyList())
    val Section1 = Section("Cjelina 1",1, listOf(AL_FATIHA,AL_BEKARA))
    val Section2 = Section("Cjelina 2",2, listOf(AL_BEKARA))
    val Section3 = Section("Cjelina 3",3, listOf(ALI_IMRAN))
    val Section4 = Section("Cjelina 4",4, listOf(AN_NISA))
    val Section5 = Section("Cjelina 5",5, listOf(AN_NISA))
    val Section6 = Section("Cjelina 6",6, listOf(AL_MAIDA))
    val Section7 = Section("Cjelina 7",7, listOf(AL_ANAM))
    val Section8 = Section("Cjelina 8",8, listOf(AL_ARAF))
    val Section9 = Section("Cjelina 9",9, listOf(AL_ENFAL))
    val Section10 = Section("Cjelina 10",10, listOf(AT_TEVBA))
    val Section11 = Section("Cjelina 11",11, listOf(JUNUS,HUD))
    val Section12 = Section("Cjelina 12",12, listOf(JUSUF))
    val Section13 = Section("Cjelina 13",13, listOf(AR_RAD, IBRAHIM))
    val Section14 = Section("Cjelina 14",14, listOf(AL_HIJR, AN_NAHL))
    val Section15 = Section("Cjelina 15",15, listOf(AL_ISRA, AL_KEHF))
    val Section16 = Section("Cjelina 16",16, listOf(MERJEM))

    val SECTIONS = listOf(Section_NULL, Section1, Section2,Section3,Section4,Section5,Section6,
        Section7,Section8,Section9,Section10,Section11,Section12,Section13,Section14,Section15,Section16)


    val TYPE_NULL = AnswerType("Svi odgovori")
    val CHAPTER = AnswerType("Poglavlje")
    val SECTION = AnswerType("Cjelina")
    val SENTENCE = AnswerType("Rečenica")

    val ANSWER_TYPES = listOf(
        TYPE_NULL,
        SECTION,
        CHAPTER,
        SENTENCE
    )






}