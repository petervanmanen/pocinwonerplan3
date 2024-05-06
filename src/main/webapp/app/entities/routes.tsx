import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanwezigedeelnemer from './aanwezigedeelnemer';
import Agendapunt from './agendapunt';
import Categorie from './categorie';
import Collegelid from './collegelid';
import Dossier from './dossier';
import Indiener from './indiener';
import Programma from './programma';
import Raadscommissie from './raadscommissie';
import Raadslid from './raadslid';
import Raadsstuk from './raadsstuk';
import Stemming from './stemming';
import Taakveld from './taakveld';
import Vergadering from './vergadering';
import Activiteit from './activiteit';
import Aomstatus from './aomstatus';
import Bevinding from './bevinding';
import Boa from './boa';
import Combibon from './combibon';
import Fietsregistratie from './fietsregistratie';
import Grondslag from './grondslag';
import Heffinggrondslag from './heffinggrondslag';
import Heffingsverordening from './heffingsverordening';
import Inspectie from './inspectie';
import Kosten from './kosten';
import Legesgrondslag from './legesgrondslag';
import Ligplaatsontheffing from './ligplaatsontheffing';
import Moraanvraagofmelding from './moraanvraagofmelding';
import Openbareactiviteit from './openbareactiviteit';
import Precario from './precario';
import Producttype from './producttype';
import Subproducttype from './subproducttype';
import Vaartuig from './vaartuig';
import Vomaanvraagofmelding from './vomaanvraagofmelding';
import Vordering from './vordering';
import Vorderingregel from './vorderingregel';
import Vthaanvraagofmelding from './vthaanvraagofmelding';
import Vthmelding from './vthmelding';
import Vthzaak from './vthzaak';
import Waarneming from './waarneming';
import Waboaanvraagofmelding from './waboaanvraagofmelding';
import Woonfraudeaanvraagofmelding from './woonfraudeaanvraagofmelding';
import Woonoverlastaanvraagofmelding from './woonoverlastaanvraagofmelding';
import Belprovider from './belprovider';
import Mulderfeit from './mulderfeit';
import Naheffing from './naheffing';
import Parkeergarage from './parkeergarage';
import Parkeerrecht from './parkeerrecht';
import Parkeerscan from './parkeerscan';
import Parkeervergunning from './parkeervergunning';
import Parkeervlak from './parkeervlak';
import Parkeerzone from './parkeerzone';
import Productgroep from './productgroep';
import Productsoort from './productsoort';
import Straatsectie from './straatsectie';
import Voertuig from './voertuig';
import Stremming from './stremming';
import Strooidag from './strooidag';
import Strooiroute from './strooiroute';
import Strooirouteuitvoering from './strooirouteuitvoering';
import Verkeersbesluit from './verkeersbesluit';
import Verkeerstelling from './verkeerstelling';
import Vloginfo from './vloginfo';
import Contact from './contact';
import Hotel from './hotel';
import Hotelbezoek from './hotelbezoek';
import Verkooppunt from './verkooppunt';
import Werkgelegenheid from './werkgelegenheid';
import Winkelvloeroppervlak from './winkelvloeroppervlak';
import Aanvraagleerlingenvervoer from './aanvraagleerlingenvervoer';
import Aanvraagofmelding from './aanvraagofmelding';
import Aanvraagvrijstelling from './aanvraagvrijstelling';
import Beschikkingleerlingenvervoer from './beschikkingleerlingenvervoer';
import Beslissing from './beslissing';
import Doorgeleidingom from './doorgeleidingom';
import Haltverwijzing from './haltverwijzing';
import Klachtleerlingenvervoer from './klachtleerlingenvervoer';
import Leerplichtambtenaar from './leerplichtambtenaar';
import Procesverbaalonderwijs from './procesverbaalonderwijs';
import Verlofaanvraag from './verlofaanvraag';
import Vervoerder from './vervoerder';
import Verzuimmelding from './verzuimmelding';
import Vrijstelling from './vrijstelling';
import Ziekmeldingleerlingenvervoer from './ziekmeldingleerlingenvervoer';
import Inschrijving from './inschrijving';
import Leerjaar from './leerjaar';
import Leerling from './leerling';
import Locatie from './locatie';
import Loopbaanstap from './loopbaanstap';
import Onderwijsloopbaan from './onderwijsloopbaan';
import Onderwijsniveau from './onderwijsniveau';
import Onderwijssoort from './onderwijssoort';
import Ouderofverzorger from './ouderofverzorger';
import School from './school';
import Startkwalificatie from './startkwalificatie';
import Uitschrijving from './uitschrijving';
import Archeologiebesluit from './archeologiebesluit';
import Artefact from './artefact';
import Artefactsoort from './artefactsoort';
import Boring from './boring';
import Doos from './doos';
import Kaart from './kaart';
import Magazijnlocatie from './magazijnlocatie';
import Magazijnplaatsing from './magazijnplaatsing';
import Project from './project';
import Put from './put';
import Spoor from './spoor';
import Stelling from './stelling';
import Vindplaats from './vindplaats';
import Vlak from './vlak';
import Vondst from './vondst';
import Vulling from './vulling';
import Aanvraag from './aanvraag';
import Archief from './archief';
import Archiefcategorie from './archiefcategorie';
import Archiefstuk from './archiefstuk';
import Auteur from './auteur';
import Bezoeker from './bezoeker';
import Depot from './depot';
import Digitaalbestand from './digitaalbestand';
import Indeling from './indeling';
import Index from './index';
import Kast from './kast';
import Naderetoegang from './naderetoegang';
import Ordeningsschema from './ordeningsschema';
import Plank from './plank';
import Rechthebbende from './rechthebbende';
import Uitgever from './uitgever';
import Erfgoedobject from './erfgoedobject';
import Historischerol from './historischerol';
import Historischpersoon from './historischpersoon';
import Eobjectclassificatie from './eobjectclassificatie';
import Ambacht from './ambacht';
import Beschermdestatus from './beschermdestatus';
import Bouwactiviteit from './bouwactiviteit';
import Bouwstijl from './bouwstijl';
import Bouwtype from './bouwtype';
import Oorspronkelijkefunctie from './oorspronkelijkefunctie';
import Activiteitsoort from './activiteitsoort';
import Balieverkoop from './balieverkoop';
import Balieverkoopentreekaart from './balieverkoopentreekaart';
import Belanghebbende from './belanghebbende';
import Belangtype from './belangtype';
import Bruikleen from './bruikleen';
import Collectie from './collectie';
import Doelgroep from './doelgroep';
import Entreekaart from './entreekaart';
import Incident from './incident';
import Lener from './lener';
import Mailing from './mailing';
import Museumobject from './museumobject';
import Museumrelatie from './museumrelatie';
import Omzetgroep from './omzetgroep';
import Prijs from './prijs';
import Product from './product';
import Productieeenheid from './productieeenheid';
import Programmasoort from './programmasoort';
import Reservering from './reservering';
import Rol from './rol';
import Rondleiding from './rondleiding';
import Samensteller from './samensteller';
import Standplaats from './standplaats';
import Tentoonstelling from './tentoonstelling';
import Voorziening from './voorziening';
import Winkelverkoopgroep from './winkelverkoopgroep';
import Winkelvoorraaditem from './winkelvoorraaditem';
import Zaal from './zaal';
import Aantal from './aantal';
import Belijning from './belijning';
import Bezetting from './bezetting';
import Binnenlocatie from './binnenlocatie';
import Onderhoudskosten from './onderhoudskosten';
import Sport from './sport';
import Sportlocatie from './sportlocatie';
import Sportmateriaal from './sportmateriaal';
import Sportpark from './sportpark';
import Sportvereniging from './sportvereniging';
import Veld from './veld';
import Gemeentebegrafenis from './gemeentebegrafenis';
import Aanvraagstadspas from './aanvraagstadspas';
import Aomaanvraagwmojeugd from './aomaanvraagwmojeugd';
import Aommeldingwmojeugd from './aommeldingwmojeugd';
import Beperking from './beperking';
import Beperkingscategorie from './beperkingscategorie';
import Beperkingscore from './beperkingscore';
import Beperkingscoresoort from './beperkingscoresoort';
import Beschikking from './beschikking';
import Beschikkingsoort from './beschikkingsoort';
import Beschiktevoorziening from './beschiktevoorziening';
import Budgetuitputting from './budgetuitputting';
import Client from './client';
import Clientbegeleider from './clientbegeleider';
import Declaratie from './declaratie';
import Declaratieregel from './declaratieregel';
import Huishouden from './huishouden';
import Leefgebied from './leefgebied';
import Leverancier from './leverancier';
import Levering from './levering';
import Leveringsvorm from './leveringsvorm';
import Meldingeigenbijdrage from './meldingeigenbijdrage';
import Pgbtoekenning from './pgbtoekenning';
import Relatie from './relatie';
import Relatiesoort from './relatiesoort';
import Score from './score';
import Scoresoort from './scoresoort';
import Stadspas from './stadspas';
import Tarief from './tarief';
import Team from './team';
import Toewijzing from './toewijzing';
import Verplichtingwmojeugd from './verplichtingwmojeugd';
import Verzoekomtoewijzing from './verzoekomtoewijzing';
import Voorzieningsoort from './voorzieningsoort';
import Zelfredzaamheidmatrix from './zelfredzaamheidmatrix';
import Asielstatushouder from './asielstatushouder';
import B1 from './b-1';
import Bredeintake from './bredeintake';
import Examen from './examen';
import Examenonderdeel from './examenonderdeel';
import Gezinsmigrantenoverigemigrant from './gezinsmigrantenoverigemigrant';
import Inburgeraar from './inburgeraar';
import Inburgeringstraject from './inburgeringstraject';
import Knm from './knm';
import Leerroute from './leerroute';
import Map from './map';
import Onderwijs from './onderwijs';
import Participatiecomponent from './participatiecomponent';
import Pip from './pip';
import Pvt from './pvt';
import Verblijfplaats from './verblijfplaats';
import Verblijfplaatsazc from './verblijfplaatsazc';
import Vreemdeling from './vreemdeling';
import Z from './z';
import Financielesituatie from './financielesituatie';
import Notarielestatus from './notarielestatus';
import Projectsoort from './projectsoort';
import Resultaat from './resultaat';
import Resultaatsoort from './resultaatsoort';
import Traject from './traject';
import Uitstroomreden from './uitstroomreden';
import Uitstroomredensoort from './uitstroomredensoort';
import Behandeling from './behandeling';
import Behandelsoort from './behandelsoort';
import Bijzonderheid from './bijzonderheid';
import Bijzonderheidsoort from './bijzonderheidsoort';
import Caseaanmelding from './caseaanmelding';
import Doelstelling from './doelstelling';
import Doelstellingsoort from './doelstellingsoort';
import Sociaalteamdossier from './sociaalteamdossier';
import Sociaalteamdossiersoort from './sociaalteamdossiersoort';
import Ecomponent from './ecomponent';
import Ecomponentsoort from './ecomponentsoort';
import Fraudegegevens from './fraudegegevens';
import Fraudesoort from './fraudesoort';
import Informatiedakloosheid from './informatiedakloosheid';
import Inkomensvoorziening from './inkomensvoorziening';
import Inkomensvoorzieningsoort from './inkomensvoorzieningsoort';
import Participatiedossier from './participatiedossier';
import Redenbeeindiging from './redenbeeindiging';
import Regeling from './regeling';
import Regelingsoort from './regelingsoort';
import Taalniveau from './taalniveau';
import Tegenprestatie from './tegenprestatie';
import Tegenprestatiehoogte from './tegenprestatiehoogte';
import Trajectactiviteit from './trajectactiviteit';
import Trajectactiviteitsoort from './trajectactiviteitsoort';
import Trajectsoort from './trajectsoort';
import Uitkeringsrun from './uitkeringsrun';
import Container from './container';
import Containertype from './containertype';
import Fractie from './fractie';
import Melding from './melding';
import Milieustraat from './milieustraat';
import Ophaalmoment from './ophaalmoment';
import Pas from './pas';
import Prijsafspraak from './prijsafspraak';
import Prijsregel from './prijsregel';
import Rit from './rit';
import Eroute from './eroute';
import Storting from './storting';
import Vuilniswagen from './vuilniswagen';
import Vulgraadmeting from './vulgraadmeting';
import Aansluitput from './aansluitput';
import Afvalbak from './afvalbak';
import Bak from './bak';
import Bank from './bank';
import Beheerobject from './beheerobject';
import Bemalingsgebied from './bemalingsgebied';
import Bergingsbassin from './bergingsbassin';
import Boom from './boom';
import Bord from './bord';
import Bouwwerk from './bouwwerk';
import Brug from './brug';
import Drainageput from './drainageput';
import Ecoduct from './ecoduct';
import Fietsparkeervoorziening from './fietsparkeervoorziening';
import Filterput from './filterput';
import Flyover from './flyover';
import Functioneelgebied from './functioneelgebied';
import Geluidsscherm from './geluidsscherm';
import Gemaal from './gemaal';
import Groenobject from './groenobject';
import Infiltratieput from './infiltratieput';
import Installatie from './installatie';
import Kademuur from './kademuur';
import Keermuur from './keermuur';
import Klimplant from './klimplant';
import Kolk from './kolk';
import Kunstwerk from './kunstwerk';
import Leiding from './leiding';
import Leidingelement from './leidingelement';
import Mast from './mast';
import Meubilair from './meubilair';
import Overbruggingsobject from './overbruggingsobject';
import Overstortconstructie from './overstortconstructie';
import Paal from './paal';
import Pomp from './pomp';
import Putdeksel from './putdeksel';
import Rioleringsgebied from './rioleringsgebied';
import Rioolput from './rioolput';
import Scheiding from './scheiding';
import Sensor from './sensor';
import Solitaireplant from './solitaireplant';
import Speelterrein from './speelterrein';
import Speeltoestel from './speeltoestel';
import Sportterrein from './sportterrein';
import Stuwgebied from './stuwgebied';
import Terreindeel from './terreindeel';
import Tunnelobject from './tunnelobject';
import Uitlaatconstructie from './uitlaatconstructie';
import Vegetatieobject from './vegetatieobject';
import Verhardingsobject from './verhardingsobject';
import Verkeersdrempel from './verkeersdrempel';
import Verlichtingsobject from './verlichtingsobject';
import Viaduct from './viaduct';
import Waterinrichtingsobject from './waterinrichtingsobject';
import Waterobject from './waterobject';
import Weginrichtingsobject from './weginrichtingsobject';
import Actie from './actie';
import Areaal from './areaal';
import Crowmelding from './crowmelding';
import Deelplanveld from './deelplanveld';
import Faseoplevering from './faseoplevering';
import Geoobject from './geoobject';
import Grondbeheerder from './grondbeheerder';
import Kadastralemutatie from './kadastralemutatie';
import Kwaliteitscatalogusopenbareruimte from './kwaliteitscatalogusopenbareruimte';
import Kwaliteitskenmerken from './kwaliteitskenmerken';
import Elogboek from './elogboek';
import Meldingongeval from './meldingongeval';
import Moormelding from './moormelding';
import Omgevingsvergunning from './omgevingsvergunning';
import Onderhoud from './onderhoud';
import Opbreking from './opbreking';
import Procesverbaalmoormelding from './procesverbaalmoormelding';
import Schouwronde from './schouwronde';
import Storing from './storing';
import Taak from './taak';
import Uitvoerdergraafwerkzaamheden from './uitvoerdergraafwerkzaamheden';
import Verkeerslicht from './verkeerslicht';
import Gebouw from './gebouw';
import Huurwoningen from './huurwoningen';
import Koopwoningen from './koopwoningen';
import Plan from './plan';
import Projectleider from './projectleider';
import Projectontwikkelaar from './projectontwikkelaar';
import Studentenwoningen from './studentenwoningen';
import Bevoegdgezag from './bevoegdgezag';
import Gemachtigde from './gemachtigde';
import Initiatiefnemer from './initiatiefnemer';
import Projectactiviteit from './projectactiviteit';
import Projectlocatie from './projectlocatie';
import Specificatie from './specificatie';
import Uitvoerendeinstantie from './uitvoerendeinstantie';
import Verzoek from './verzoek';
import Omgevingsdocument from './omgevingsdocument';
import Regeltekst from './regeltekst';
import Beperkingsgebied from './beperkingsgebied';
import Functie from './functie';
import Gebiedsaanwijzing from './gebiedsaanwijzing';
import Idealisatie from './idealisatie';
import Instructieregel from './instructieregel';
import Juridischeregel from './juridischeregel';
import Norm from './norm';
import Normwaarde from './normwaarde';
import Omgevingsnorm from './omgevingsnorm';
import Omgevingswaarde from './omgevingswaarde';
import Omgevingswaarderegel from './omgevingswaarderegel';
import Regelvooriedereen from './regelvooriedereen';
import Thema from './thema';
import Conclusie from './conclusie';
import Indieningsvereisten from './indieningsvereisten';
import Maatregelen from './maatregelen';
import Toepasbareregel from './toepasbareregel';
import Toepasbareregelbestand from './toepasbareregelbestand';
import Uitvoeringsregel from './uitvoeringsregel';
import Applicatie from './applicatie';
import Attribuutsoort from './attribuutsoort';
import Classificatie from './classificatie';
import Cmdbitem from './cmdbitem';
import Database from './database';
import Datatype from './datatype';
import Dienst from './dienst';
import Domeinoftaakveld from './domeinoftaakveld';
import Externebron from './externebron';
import Gebruikerrol from './gebruikerrol';
import Gegeven from './gegeven';
import Generalisatie from './generalisatie';
import Hardware from './hardware';
import Inventaris from './inventaris';
import Koppeling from './koppeling';
import Licentie from './licentie';
import Linkbaarcmdbitem from './linkbaarcmdbitem';
import Elog from './elog';
import Nertwerkcomponent from './nertwerkcomponent';
import Notitie from './notitie';
import Eobjecttype from './eobjecttype';
import Onderwerp from './onderwerp';
import Epackage from './epackage';
import Prijzenboek from './prijzenboek';
import Server from './server';
import Software from './software';
import Telefoniegegevens from './telefoniegegevens';
import Toegangsmiddel from './toegangsmiddel';
import Versie from './versie';
import Vervoersmiddel from './vervoersmiddel';
import Wijzigingsverzoek from './wijzigingsverzoek';
import Betaalmoment from './betaalmoment';
import Rapportagemoment from './rapportagemoment';
import Sector from './sector';
import Subsidie from './subsidie';
import Subsidieaanvraag from './subsidieaanvraag';
import Subsidiebeschikking from './subsidiebeschikking';
import Subsidiecomponent from './subsidiecomponent';
import Subsidieprogramma from './subsidieprogramma';
import Aanbestedingvastgoed from './aanbestedingvastgoed';
import Adresaanduiding from './adresaanduiding';
import Bouwdeel from './bouwdeel';
import Bouwdeelelement from './bouwdeelelement';
import Cultuuronbebouwd from './cultuuronbebouwd';
import Eigenaar from './eigenaar';
import Gebruiksdoel from './gebruiksdoel';
import Huurder from './huurder';
import Kpbetrokkenbij from './kpbetrokkenbij';
import Kponstaanuit from './kponstaanuit';
import Locatieaanduidingwozobject from './locatieaanduidingwozobject';
import Locatieonroerendezaak from './locatieonroerendezaak';
import Mjop from './mjop';
import Mjopitem from './mjopitem';
import Nadaanvullingbrp from './nadaanvullingbrp';
import Eobjectrelatie from './eobjectrelatie';
import Offerte from './offerte';
import Pachter from './pachter';
import Prijzenboekitem from './prijzenboekitem';
import Vastgoedcontract from './vastgoedcontract';
import Vastgoedcontractregel from './vastgoedcontractregel';
import Vastgoedobject from './vastgoedobject';
import Verhuurbaareenheid from './verhuurbaareenheid';
import Werkbon from './werkbon';
import Wozbelang from './wozbelang';
import Zakelijkrecht from './zakelijkrecht';
import Bedrijf from './bedrijf';
import Activa from './activa';
import Activasoort from './activasoort';
import Bankafschrift from './bankafschrift';
import Bankafschriftregel from './bankafschriftregel';
import Bankrekening from './bankrekening';
import Batch from './batch';
import Batchregel from './batchregel';
import Begroting from './begroting';
import Begrotingregel from './begrotingregel';
import Debiteur from './debiteur';
import Factuur from './factuur';
import Factuurregel from './factuurregel';
import Hoofdrekening from './hoofdrekening';
import Hoofdstuk from './hoofdstuk';
import Inkooporder from './inkooporder';
import Kostenplaats from './kostenplaats';
import Mutatie from './mutatie';
import Opdrachtgever from './opdrachtgever';
import Opdrachtnemer from './opdrachtnemer';
import Subrekening from './subrekening';
import Werkorder from './werkorder';
import Beoordeling from './beoordeling';
import Declaratiesoort from './declaratiesoort';
import Dienstverband from './dienstverband';
import Disciplinairemaatregel from './disciplinairemaatregel';
import Formatieplaats from './formatieplaats';
import Functiehuis from './functiehuis';
import Genotenopleiding from './genotenopleiding';
import Geweldsincident from './geweldsincident';
import Individueelkeuzebudget from './individueelkeuzebudget';
import Inzet from './inzet';
import Keuzebudgetbesteding from './keuzebudgetbesteding';
import Keuzebudgetbestedingsoort from './keuzebudgetbestedingsoort';
import Normprofiel from './normprofiel';
import Onderwijsinstituut from './onderwijsinstituut';
import Opleiding from './opleiding';
import Organisatorischeeenheidhr from './organisatorischeeenheidhr';
import Sollicitant from './sollicitant';
import Sollicitatie from './sollicitatie';
import Sollicitatiegesprek from './sollicitatiegesprek';
import Soortdisciplinairemaatregel from './soortdisciplinairemaatregel';
import Uren from './uren';
import Vacature from './vacature';
import Verlof from './verlof';
import Verlofsoort from './verlofsoort';
import Verzuim from './verzuim';
import Verzuimsoort from './verzuimsoort';
import Werknemer from './werknemer';
import Aanbesteding from './aanbesteding';
import Aanbestedinginhuur from './aanbestedinginhuur';
import Aankondiging from './aankondiging';
import Aanvraaginkooporder from './aanvraaginkooporder';
import Contract from './contract';
import Cpvcode from './cpvcode';
import Formulierinhuur from './formulierinhuur';
import Formulierverlenginginhuur from './formulierverlenginginhuur';
import Gunning from './gunning';
import Inkooppakket from './inkooppakket';
import Kandidaat from './kandidaat';
import Kwalificatie from './kwalificatie';
import Offerteaanvraag from './offerteaanvraag';
import Selectietabelaanbesteding from './selectietabelaanbesteding';
import Startformulieraanbesteden from './startformulieraanbesteden';
import Uitnodiging from './uitnodiging';
import Aanvraagdata from './aanvraagdata';
import Afspraakstatus from './afspraakstatus';
import Artikel from './artikel';
import Balieafspraak from './balieafspraak';
import Formuliersoort from './formuliersoort';
import Formuliersoortveld from './formuliersoortveld';
import Klantbeoordeling from './klantbeoordeling';
import Klantbeoordelingreden from './klantbeoordelingreden';
import Productofdienst from './productofdienst';
import Telefoononderwerp from './telefoononderwerp';
import Telefoonstatus from './telefoonstatus';
import Telefoontje from './telefoontje';
import Nummeraanduiding from './nummeraanduiding';
import Buurt from './buurt';
import Gemeente from './gemeente';
import Ligplaats from './ligplaats';
import Adresseerbaarobject from './adresseerbaarobject';
import Openbareruimte from './openbareruimte';
import Pand from './pand';
import Verblijfsobject from './verblijfsobject';
import Wijk from './wijk';
import Woonplaats from './woonplaats';
import Periode from './periode';
import Foto from './foto';
import Gebied from './gebied';
import Gebiedengroep from './gebiedengroep';
import Lijn from './lijn';
import Lijnengroep from './lijnengroep';
import Punt from './punt';
import Puntengroep from './puntengroep';
import Videoopname from './videoopname';
import Bedrijfsproces from './bedrijfsproces';
import Bedrijfsprocestype from './bedrijfsprocestype';
import Besluit from './besluit';
import Besluittype from './besluittype';
import Betaling from './betaling';
import Betrokkene from './betrokkene';
import Deelproces from './deelproces';
import Deelprocestype from './deelprocestype';
import Document from './document';
import Documenttype from './documenttype';
import Enkelvoudigdocument from './enkelvoudigdocument';
import Heffing from './heffing';
import Identificatiekenmerk from './identificatiekenmerk';
import Klantcontact from './klantcontact';
import Medewerker from './medewerker';
import Eobject from './eobject';
import Organisatorischeeenheid from './organisatorischeeenheid';
import Samengestelddocument from './samengestelddocument';
import Status from './status';
import Statustype from './statustype';
import Vestigingvanzaakbehandelendeorganisatie from './vestigingvanzaakbehandelendeorganisatie';
import Zaak from './zaak';
import Zaakorigineel from './zaakorigineel';
import Zaaktype from './zaaktype';
import Afwijkendbuitenlandscorrespondentieadresrol from './afwijkendbuitenlandscorrespondentieadresrol';
import Afwijkendcorrespondentiepostadresrol from './afwijkendcorrespondentiepostadresrol';
import Anderzaakobjectzaak from './anderzaakobjectzaak';
import Contactpersoonrol from './contactpersoonrol';
import Kenmerkenzaak from './kenmerkenzaak';
import Opschortingzaak from './opschortingzaak';
import Verlengingzaak from './verlengingzaak';
import Brondocumenten from './brondocumenten';
import Formelehistorie from './formelehistorie';
import Inonderzoek from './inonderzoek';
import Materielehistorie from './materielehistorie';
import Strijdigheidofnietigheid from './strijdigheidofnietigheid';
import Aantekening from './aantekening';
import Adresbuitenland from './adresbuitenland';
import Briefadres from './briefadres';
import Nationaliteit from './nationaliteit';
import Onbestemdadres from './onbestemdadres';
import Appartementsrecht from './appartementsrecht';
import Appartementsrechtsplitsing from './appartementsrechtsplitsing';
import Begroeidterreindeel from './begroeidterreindeel';
import Benoemdobject from './benoemdobject';
import Benoemdterrein from './benoemdterrein';
import Gebouwdobject from './gebouwdobject';
import Gebouwinstallatie from './gebouwinstallatie';
import Inrichtingselement from './inrichtingselement';
import Kadastraalperceel from './kadastraalperceel';
import Kadastraleonroerendezaak from './kadastraleonroerendezaak';
import Ingeschrevenpersoon from './ingeschrevenpersoon';
import Kadastraleonroerendezaakaantekening from './kadastraleonroerendezaakaantekening';
import Ingezetene from './ingezetene';
import Kunstwerkdeel from './kunstwerkdeel';
import Maatschappelijkeactiviteit from './maatschappelijkeactiviteit';
import Adresseerbaarobjectaanduiding from './adresseerbaarobjectaanduiding';
import Onbegroeidterreindeel from './onbegroeidterreindeel';
import Ondersteunendwaterdeel from './ondersteunendwaterdeel';
import Ondersteunendwegdeel from './ondersteunendwegdeel';
import Overbruggingsdeel from './overbruggingsdeel';
import Overigbenoemdterrein from './overigbenoemdterrein';
import Natuurlijkpersoon from './natuurlijkpersoon';
import Overigbouwwerk from './overigbouwwerk';
import Overiggebouwdobject from './overiggebouwdobject';
import Nietnatuurlijkpersoon from './nietnatuurlijkpersoon';
import Overigeadresseerbaarobjectaanduiding from './overigeadresseerbaarobjectaanduiding';
import Overigescheiding from './overigescheiding';
import Reisdocument from './reisdocument';
import Rechtspersoon from './rechtspersoon';
import Tenaamstelling from './tenaamstelling';
import Tunneldeel from './tunneldeel';
import Vestiging from './vestiging';
import Waterdeel from './waterdeel';
import Wegdeel from './wegdeel';
import Verblijfstitel from './verblijfstitel';
import Wozdeelobject from './wozdeelobject';
import Wozobject from './wozobject';
import Wozwaarde from './wozwaarde';
import Zekerheidsrecht from './zekerheidsrecht';
import Locatieaanduidingadreswozobject from './locatieaanduidingadreswozobject';
import Correspondentieadresbuitenland from './correspondentieadresbuitenland';
import Geboorteingeschrevennatuurlijkpersoon from './geboorteingeschrevennatuurlijkpersoon';
import Geboorteingeschrevenpersoon from './geboorteingeschrevenpersoon';
import Handelsnamenmaatschappelijkeactiviteit from './handelsnamenmaatschappelijkeactiviteit';
import Handelsnamenvestiging from './handelsnamenvestiging';
import Koopsomkadastraleonroerendezaak from './koopsomkadastraleonroerendezaak';
import Locatiekadastraleonroerendezaak from './locatiekadastraleonroerendezaak';
import Migratieingeschrevennatuurlijkpersoon from './migratieingeschrevennatuurlijkpersoon';
import Naamaanschrijvingnatuurlijkpersoon from './naamaanschrijvingnatuurlijkpersoon';
import Naamgebruiknatuurlijkpersoon from './naamgebruiknatuurlijkpersoon';
import Naamnatuurlijkpersoon from './naamnatuurlijkpersoon';
import Nationaliteitingeschrevennatuurlijkpersoon from './nationaliteitingeschrevennatuurlijkpersoon';
import Nederlandsenationaliteitingeschrevenpersoon from './nederlandsenationaliteitingeschrevenpersoon';
import Ontbindinghuwelijkgeregistreerdpartnerschap from './ontbindinghuwelijkgeregistreerdpartnerschap';
import Overlijdeningeschrevennatuurlijkpersoon from './overlijdeningeschrevennatuurlijkpersoon';
import Overlijdeningeschrevenpersoon from './overlijdeningeschrevenpersoon';
import Postadres from './postadres';
import Rekeningnummer from './rekeningnummer';
import Samengesteldenaamnatuurlijkpersoon from './samengesteldenaamnatuurlijkpersoon';
import Sbiactiviteitvestiging from './sbiactiviteitvestiging';
import Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap from './sluitingofaangaanhuwelijkofgeregistreerdpartnerschap';
import Soortfunctioneelgebied from './soortfunctioneelgebied';
import Soortkunstwerk from './soortkunstwerk';
import Soortoverigbouwwerk from './soortoverigbouwwerk';
import Soortscheiding from './soortscheiding';
import Soortspoor from './soortspoor';
import Splitsingstekeningreferentie from './splitsingstekeningreferentie';
import Verblijfadresingeschrevennatuurlijkpersoon from './verblijfadresingeschrevennatuurlijkpersoon';
import Verblijfadresingeschrevenpersoon from './verblijfadresingeschrevenpersoon';
import Verblijfbuitenland from './verblijfbuitenland';
import Verblijfbuitenlandsubject from './verblijfbuitenlandsubject';
import Verblijfsrechtingeschrevennatuurlijkpersoon from './verblijfsrechtingeschrevennatuurlijkpersoon';
import Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon from './verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon';
import Aanduidingverblijfsrecht from './aanduidingverblijfsrecht';
import Autoriteitafgiftenederlandsreisdocument from './autoriteitafgiftenederlandsreisdocument';
import Aardaantekening from './aardaantekening';
import Aardzakelijkrecht from './aardzakelijkrecht';
import Aardfiliatie from './aardfiliatie';
import Academischetitel from './academischetitel';
import Akrkadastralegemeentecode from './akrkadastralegemeentecode';
import Cultuurcodebebouwd from './cultuurcodebebouwd';
import Cultuurcodeonbebouwd from './cultuurcodeonbebouwd';
import Wozdeelobjectcode from './wozdeelobjectcode';
import Kadastralegemeente from './kadastralegemeente';
import Landofgebied from './landofgebied';
import Provincie from './provincie';
import Partij from './partij';
import Redenverkrijgingnationaliteit from './redenverkrijgingnationaliteit';
import Redenverliesnationaliteit from './redenverliesnationaliteit';
import Reisdocumentsoort from './reisdocumentsoort';
import Sbiactiviteit from './sbiactiviteit';
import Soortgrootte from './soortgrootte';
import Soortwozobject from './soortwozobject';
import Valutasoort from './valutasoort';
import Land from './land';
import Valuta from './valuta';
import Eobjecttypea from './eobjecttypea';
import Eobjecttypeb from './eobjecttypeb';
import Eobjecttypec from './eobjecttypec';
import Eobjecttyped from './eobjecttyped';
import Eobjecttypee from './eobjecttypee';
import Eobjecttypef from './eobjecttypef';
import Eobjecttypeg from './eobjecttypeg';
import Childclassa from './childclassa';
import Classa from './classa';
import Classb from './classb';
import Classc from './classc';
import Class1 from './class-1';
import Classd from './classd';
import Classe from './classe';
import Classf from './classf';
import Classg from './classg';
import Classh from './classh';
import Classi from './classi';
import Classj from './classj';
import Kpclassaclassc from './kpclassaclassc';
import Enumenumerationa from './enumenumerationa';
import Lstclass1 from './lstclass-1';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="aanwezigedeelnemer/*" element={<Aanwezigedeelnemer />} />
        <Route path="agendapunt/*" element={<Agendapunt />} />
        <Route path="categorie/*" element={<Categorie />} />
        <Route path="collegelid/*" element={<Collegelid />} />
        <Route path="dossier/*" element={<Dossier />} />
        <Route path="indiener/*" element={<Indiener />} />
        <Route path="programma/*" element={<Programma />} />
        <Route path="raadscommissie/*" element={<Raadscommissie />} />
        <Route path="raadslid/*" element={<Raadslid />} />
        <Route path="raadsstuk/*" element={<Raadsstuk />} />
        <Route path="stemming/*" element={<Stemming />} />
        <Route path="taakveld/*" element={<Taakveld />} />
        <Route path="vergadering/*" element={<Vergadering />} />
        <Route path="activiteit/*" element={<Activiteit />} />
        <Route path="aomstatus/*" element={<Aomstatus />} />
        <Route path="bevinding/*" element={<Bevinding />} />
        <Route path="boa/*" element={<Boa />} />
        <Route path="combibon/*" element={<Combibon />} />
        <Route path="fietsregistratie/*" element={<Fietsregistratie />} />
        <Route path="grondslag/*" element={<Grondslag />} />
        <Route path="heffinggrondslag/*" element={<Heffinggrondslag />} />
        <Route path="heffingsverordening/*" element={<Heffingsverordening />} />
        <Route path="inspectie/*" element={<Inspectie />} />
        <Route path="kosten/*" element={<Kosten />} />
        <Route path="legesgrondslag/*" element={<Legesgrondslag />} />
        <Route path="ligplaatsontheffing/*" element={<Ligplaatsontheffing />} />
        <Route path="moraanvraagofmelding/*" element={<Moraanvraagofmelding />} />
        <Route path="openbareactiviteit/*" element={<Openbareactiviteit />} />
        <Route path="precario/*" element={<Precario />} />
        <Route path="producttype/*" element={<Producttype />} />
        <Route path="subproducttype/*" element={<Subproducttype />} />
        <Route path="vaartuig/*" element={<Vaartuig />} />
        <Route path="vomaanvraagofmelding/*" element={<Vomaanvraagofmelding />} />
        <Route path="vordering/*" element={<Vordering />} />
        <Route path="vorderingregel/*" element={<Vorderingregel />} />
        <Route path="vthaanvraagofmelding/*" element={<Vthaanvraagofmelding />} />
        <Route path="vthmelding/*" element={<Vthmelding />} />
        <Route path="vthzaak/*" element={<Vthzaak />} />
        <Route path="waarneming/*" element={<Waarneming />} />
        <Route path="waboaanvraagofmelding/*" element={<Waboaanvraagofmelding />} />
        <Route path="woonfraudeaanvraagofmelding/*" element={<Woonfraudeaanvraagofmelding />} />
        <Route path="woonoverlastaanvraagofmelding/*" element={<Woonoverlastaanvraagofmelding />} />
        <Route path="belprovider/*" element={<Belprovider />} />
        <Route path="mulderfeit/*" element={<Mulderfeit />} />
        <Route path="naheffing/*" element={<Naheffing />} />
        <Route path="parkeergarage/*" element={<Parkeergarage />} />
        <Route path="parkeerrecht/*" element={<Parkeerrecht />} />
        <Route path="parkeerscan/*" element={<Parkeerscan />} />
        <Route path="parkeervergunning/*" element={<Parkeervergunning />} />
        <Route path="parkeervlak/*" element={<Parkeervlak />} />
        <Route path="parkeerzone/*" element={<Parkeerzone />} />
        <Route path="productgroep/*" element={<Productgroep />} />
        <Route path="productsoort/*" element={<Productsoort />} />
        <Route path="straatsectie/*" element={<Straatsectie />} />
        <Route path="voertuig/*" element={<Voertuig />} />
        <Route path="stremming/*" element={<Stremming />} />
        <Route path="strooidag/*" element={<Strooidag />} />
        <Route path="strooiroute/*" element={<Strooiroute />} />
        <Route path="strooirouteuitvoering/*" element={<Strooirouteuitvoering />} />
        <Route path="verkeersbesluit/*" element={<Verkeersbesluit />} />
        <Route path="verkeerstelling/*" element={<Verkeerstelling />} />
        <Route path="vloginfo/*" element={<Vloginfo />} />
        <Route path="contact/*" element={<Contact />} />
        <Route path="hotel/*" element={<Hotel />} />
        <Route path="hotelbezoek/*" element={<Hotelbezoek />} />
        <Route path="verkooppunt/*" element={<Verkooppunt />} />
        <Route path="werkgelegenheid/*" element={<Werkgelegenheid />} />
        <Route path="winkelvloeroppervlak/*" element={<Winkelvloeroppervlak />} />
        <Route path="aanvraagleerlingenvervoer/*" element={<Aanvraagleerlingenvervoer />} />
        <Route path="aanvraagofmelding/*" element={<Aanvraagofmelding />} />
        <Route path="aanvraagvrijstelling/*" element={<Aanvraagvrijstelling />} />
        <Route path="beschikkingleerlingenvervoer/*" element={<Beschikkingleerlingenvervoer />} />
        <Route path="beslissing/*" element={<Beslissing />} />
        <Route path="doorgeleidingom/*" element={<Doorgeleidingom />} />
        <Route path="haltverwijzing/*" element={<Haltverwijzing />} />
        <Route path="klachtleerlingenvervoer/*" element={<Klachtleerlingenvervoer />} />
        <Route path="leerplichtambtenaar/*" element={<Leerplichtambtenaar />} />
        <Route path="procesverbaalonderwijs/*" element={<Procesverbaalonderwijs />} />
        <Route path="verlofaanvraag/*" element={<Verlofaanvraag />} />
        <Route path="vervoerder/*" element={<Vervoerder />} />
        <Route path="verzuimmelding/*" element={<Verzuimmelding />} />
        <Route path="vrijstelling/*" element={<Vrijstelling />} />
        <Route path="ziekmeldingleerlingenvervoer/*" element={<Ziekmeldingleerlingenvervoer />} />
        <Route path="inschrijving/*" element={<Inschrijving />} />
        <Route path="leerjaar/*" element={<Leerjaar />} />
        <Route path="leerling/*" element={<Leerling />} />
        <Route path="locatie/*" element={<Locatie />} />
        <Route path="loopbaanstap/*" element={<Loopbaanstap />} />
        <Route path="onderwijsloopbaan/*" element={<Onderwijsloopbaan />} />
        <Route path="onderwijsniveau/*" element={<Onderwijsniveau />} />
        <Route path="onderwijssoort/*" element={<Onderwijssoort />} />
        <Route path="ouderofverzorger/*" element={<Ouderofverzorger />} />
        <Route path="school/*" element={<School />} />
        <Route path="startkwalificatie/*" element={<Startkwalificatie />} />
        <Route path="uitschrijving/*" element={<Uitschrijving />} />
        <Route path="archeologiebesluit/*" element={<Archeologiebesluit />} />
        <Route path="artefact/*" element={<Artefact />} />
        <Route path="artefactsoort/*" element={<Artefactsoort />} />
        <Route path="boring/*" element={<Boring />} />
        <Route path="doos/*" element={<Doos />} />
        <Route path="kaart/*" element={<Kaart />} />
        <Route path="magazijnlocatie/*" element={<Magazijnlocatie />} />
        <Route path="magazijnplaatsing/*" element={<Magazijnplaatsing />} />
        <Route path="project/*" element={<Project />} />
        <Route path="put/*" element={<Put />} />
        <Route path="spoor/*" element={<Spoor />} />
        <Route path="stelling/*" element={<Stelling />} />
        <Route path="vindplaats/*" element={<Vindplaats />} />
        <Route path="vlak/*" element={<Vlak />} />
        <Route path="vondst/*" element={<Vondst />} />
        <Route path="vulling/*" element={<Vulling />} />
        <Route path="aanvraag/*" element={<Aanvraag />} />
        <Route path="archief/*" element={<Archief />} />
        <Route path="archiefcategorie/*" element={<Archiefcategorie />} />
        <Route path="archiefstuk/*" element={<Archiefstuk />} />
        <Route path="auteur/*" element={<Auteur />} />
        <Route path="bezoeker/*" element={<Bezoeker />} />
        <Route path="depot/*" element={<Depot />} />
        <Route path="digitaalbestand/*" element={<Digitaalbestand />} />
        <Route path="indeling/*" element={<Indeling />} />
        <Route path="index/*" element={<Index />} />
        <Route path="kast/*" element={<Kast />} />
        <Route path="naderetoegang/*" element={<Naderetoegang />} />
        <Route path="ordeningsschema/*" element={<Ordeningsschema />} />
        <Route path="plank/*" element={<Plank />} />
        <Route path="rechthebbende/*" element={<Rechthebbende />} />
        <Route path="uitgever/*" element={<Uitgever />} />
        <Route path="erfgoedobject/*" element={<Erfgoedobject />} />
        <Route path="historischerol/*" element={<Historischerol />} />
        <Route path="historischpersoon/*" element={<Historischpersoon />} />
        <Route path="eobjectclassificatie/*" element={<Eobjectclassificatie />} />
        <Route path="ambacht/*" element={<Ambacht />} />
        <Route path="beschermdestatus/*" element={<Beschermdestatus />} />
        <Route path="bouwactiviteit/*" element={<Bouwactiviteit />} />
        <Route path="bouwstijl/*" element={<Bouwstijl />} />
        <Route path="bouwtype/*" element={<Bouwtype />} />
        <Route path="oorspronkelijkefunctie/*" element={<Oorspronkelijkefunctie />} />
        <Route path="activiteitsoort/*" element={<Activiteitsoort />} />
        <Route path="balieverkoop/*" element={<Balieverkoop />} />
        <Route path="balieverkoopentreekaart/*" element={<Balieverkoopentreekaart />} />
        <Route path="belanghebbende/*" element={<Belanghebbende />} />
        <Route path="belangtype/*" element={<Belangtype />} />
        <Route path="bruikleen/*" element={<Bruikleen />} />
        <Route path="collectie/*" element={<Collectie />} />
        <Route path="doelgroep/*" element={<Doelgroep />} />
        <Route path="entreekaart/*" element={<Entreekaart />} />
        <Route path="incident/*" element={<Incident />} />
        <Route path="lener/*" element={<Lener />} />
        <Route path="mailing/*" element={<Mailing />} />
        <Route path="museumobject/*" element={<Museumobject />} />
        <Route path="museumrelatie/*" element={<Museumrelatie />} />
        <Route path="omzetgroep/*" element={<Omzetgroep />} />
        <Route path="prijs/*" element={<Prijs />} />
        <Route path="product/*" element={<Product />} />
        <Route path="productieeenheid/*" element={<Productieeenheid />} />
        <Route path="programmasoort/*" element={<Programmasoort />} />
        <Route path="reservering/*" element={<Reservering />} />
        <Route path="rol/*" element={<Rol />} />
        <Route path="rondleiding/*" element={<Rondleiding />} />
        <Route path="samensteller/*" element={<Samensteller />} />
        <Route path="standplaats/*" element={<Standplaats />} />
        <Route path="tentoonstelling/*" element={<Tentoonstelling />} />
        <Route path="voorziening/*" element={<Voorziening />} />
        <Route path="winkelverkoopgroep/*" element={<Winkelverkoopgroep />} />
        <Route path="winkelvoorraaditem/*" element={<Winkelvoorraaditem />} />
        <Route path="zaal/*" element={<Zaal />} />
        <Route path="aantal/*" element={<Aantal />} />
        <Route path="belijning/*" element={<Belijning />} />
        <Route path="bezetting/*" element={<Bezetting />} />
        <Route path="binnenlocatie/*" element={<Binnenlocatie />} />
        <Route path="onderhoudskosten/*" element={<Onderhoudskosten />} />
        <Route path="sport/*" element={<Sport />} />
        <Route path="sportlocatie/*" element={<Sportlocatie />} />
        <Route path="sportmateriaal/*" element={<Sportmateriaal />} />
        <Route path="sportpark/*" element={<Sportpark />} />
        <Route path="sportvereniging/*" element={<Sportvereniging />} />
        <Route path="veld/*" element={<Veld />} />
        <Route path="gemeentebegrafenis/*" element={<Gemeentebegrafenis />} />
        <Route path="aanvraagstadspas/*" element={<Aanvraagstadspas />} />
        <Route path="aomaanvraagwmojeugd/*" element={<Aomaanvraagwmojeugd />} />
        <Route path="aommeldingwmojeugd/*" element={<Aommeldingwmojeugd />} />
        <Route path="beperking/*" element={<Beperking />} />
        <Route path="beperkingscategorie/*" element={<Beperkingscategorie />} />
        <Route path="beperkingscore/*" element={<Beperkingscore />} />
        <Route path="beperkingscoresoort/*" element={<Beperkingscoresoort />} />
        <Route path="beschikking/*" element={<Beschikking />} />
        <Route path="beschikkingsoort/*" element={<Beschikkingsoort />} />
        <Route path="beschiktevoorziening/*" element={<Beschiktevoorziening />} />
        <Route path="budgetuitputting/*" element={<Budgetuitputting />} />
        <Route path="client/*" element={<Client />} />
        <Route path="clientbegeleider/*" element={<Clientbegeleider />} />
        <Route path="declaratie/*" element={<Declaratie />} />
        <Route path="declaratieregel/*" element={<Declaratieregel />} />
        <Route path="huishouden/*" element={<Huishouden />} />
        <Route path="leefgebied/*" element={<Leefgebied />} />
        <Route path="leverancier/*" element={<Leverancier />} />
        <Route path="levering/*" element={<Levering />} />
        <Route path="leveringsvorm/*" element={<Leveringsvorm />} />
        <Route path="meldingeigenbijdrage/*" element={<Meldingeigenbijdrage />} />
        <Route path="pgbtoekenning/*" element={<Pgbtoekenning />} />
        <Route path="relatie/*" element={<Relatie />} />
        <Route path="relatiesoort/*" element={<Relatiesoort />} />
        <Route path="score/*" element={<Score />} />
        <Route path="scoresoort/*" element={<Scoresoort />} />
        <Route path="stadspas/*" element={<Stadspas />} />
        <Route path="tarief/*" element={<Tarief />} />
        <Route path="team/*" element={<Team />} />
        <Route path="toewijzing/*" element={<Toewijzing />} />
        <Route path="verplichtingwmojeugd/*" element={<Verplichtingwmojeugd />} />
        <Route path="verzoekomtoewijzing/*" element={<Verzoekomtoewijzing />} />
        <Route path="voorzieningsoort/*" element={<Voorzieningsoort />} />
        <Route path="zelfredzaamheidmatrix/*" element={<Zelfredzaamheidmatrix />} />
        <Route path="asielstatushouder/*" element={<Asielstatushouder />} />
        <Route path="b-1/*" element={<B1 />} />
        <Route path="bredeintake/*" element={<Bredeintake />} />
        <Route path="examen/*" element={<Examen />} />
        <Route path="examenonderdeel/*" element={<Examenonderdeel />} />
        <Route path="gezinsmigrantenoverigemigrant/*" element={<Gezinsmigrantenoverigemigrant />} />
        <Route path="inburgeraar/*" element={<Inburgeraar />} />
        <Route path="inburgeringstraject/*" element={<Inburgeringstraject />} />
        <Route path="knm/*" element={<Knm />} />
        <Route path="leerroute/*" element={<Leerroute />} />
        <Route path="map/*" element={<Map />} />
        <Route path="onderwijs/*" element={<Onderwijs />} />
        <Route path="participatiecomponent/*" element={<Participatiecomponent />} />
        <Route path="pip/*" element={<Pip />} />
        <Route path="pvt/*" element={<Pvt />} />
        <Route path="verblijfplaats/*" element={<Verblijfplaats />} />
        <Route path="verblijfplaatsazc/*" element={<Verblijfplaatsazc />} />
        <Route path="vreemdeling/*" element={<Vreemdeling />} />
        <Route path="z/*" element={<Z />} />
        <Route path="financielesituatie/*" element={<Financielesituatie />} />
        <Route path="notarielestatus/*" element={<Notarielestatus />} />
        <Route path="projectsoort/*" element={<Projectsoort />} />
        <Route path="resultaat/*" element={<Resultaat />} />
        <Route path="resultaatsoort/*" element={<Resultaatsoort />} />
        <Route path="traject/*" element={<Traject />} />
        <Route path="uitstroomreden/*" element={<Uitstroomreden />} />
        <Route path="uitstroomredensoort/*" element={<Uitstroomredensoort />} />
        <Route path="behandeling/*" element={<Behandeling />} />
        <Route path="behandelsoort/*" element={<Behandelsoort />} />
        <Route path="bijzonderheid/*" element={<Bijzonderheid />} />
        <Route path="bijzonderheidsoort/*" element={<Bijzonderheidsoort />} />
        <Route path="caseaanmelding/*" element={<Caseaanmelding />} />
        <Route path="doelstelling/*" element={<Doelstelling />} />
        <Route path="doelstellingsoort/*" element={<Doelstellingsoort />} />
        <Route path="sociaalteamdossier/*" element={<Sociaalteamdossier />} />
        <Route path="sociaalteamdossiersoort/*" element={<Sociaalteamdossiersoort />} />
        <Route path="ecomponent/*" element={<Ecomponent />} />
        <Route path="ecomponentsoort/*" element={<Ecomponentsoort />} />
        <Route path="fraudegegevens/*" element={<Fraudegegevens />} />
        <Route path="fraudesoort/*" element={<Fraudesoort />} />
        <Route path="informatiedakloosheid/*" element={<Informatiedakloosheid />} />
        <Route path="inkomensvoorziening/*" element={<Inkomensvoorziening />} />
        <Route path="inkomensvoorzieningsoort/*" element={<Inkomensvoorzieningsoort />} />
        <Route path="participatiedossier/*" element={<Participatiedossier />} />
        <Route path="redenbeeindiging/*" element={<Redenbeeindiging />} />
        <Route path="regeling/*" element={<Regeling />} />
        <Route path="regelingsoort/*" element={<Regelingsoort />} />
        <Route path="taalniveau/*" element={<Taalniveau />} />
        <Route path="tegenprestatie/*" element={<Tegenprestatie />} />
        <Route path="tegenprestatiehoogte/*" element={<Tegenprestatiehoogte />} />
        <Route path="trajectactiviteit/*" element={<Trajectactiviteit />} />
        <Route path="trajectactiviteitsoort/*" element={<Trajectactiviteitsoort />} />
        <Route path="trajectsoort/*" element={<Trajectsoort />} />
        <Route path="uitkeringsrun/*" element={<Uitkeringsrun />} />
        <Route path="container/*" element={<Container />} />
        <Route path="containertype/*" element={<Containertype />} />
        <Route path="fractie/*" element={<Fractie />} />
        <Route path="melding/*" element={<Melding />} />
        <Route path="milieustraat/*" element={<Milieustraat />} />
        <Route path="ophaalmoment/*" element={<Ophaalmoment />} />
        <Route path="pas/*" element={<Pas />} />
        <Route path="prijsafspraak/*" element={<Prijsafspraak />} />
        <Route path="prijsregel/*" element={<Prijsregel />} />
        <Route path="rit/*" element={<Rit />} />
        <Route path="eroute/*" element={<Eroute />} />
        <Route path="storting/*" element={<Storting />} />
        <Route path="vuilniswagen/*" element={<Vuilniswagen />} />
        <Route path="vulgraadmeting/*" element={<Vulgraadmeting />} />
        <Route path="aansluitput/*" element={<Aansluitput />} />
        <Route path="afvalbak/*" element={<Afvalbak />} />
        <Route path="bak/*" element={<Bak />} />
        <Route path="bank/*" element={<Bank />} />
        <Route path="beheerobject/*" element={<Beheerobject />} />
        <Route path="bemalingsgebied/*" element={<Bemalingsgebied />} />
        <Route path="bergingsbassin/*" element={<Bergingsbassin />} />
        <Route path="boom/*" element={<Boom />} />
        <Route path="bord/*" element={<Bord />} />
        <Route path="bouwwerk/*" element={<Bouwwerk />} />
        <Route path="brug/*" element={<Brug />} />
        <Route path="drainageput/*" element={<Drainageput />} />
        <Route path="ecoduct/*" element={<Ecoduct />} />
        <Route path="fietsparkeervoorziening/*" element={<Fietsparkeervoorziening />} />
        <Route path="filterput/*" element={<Filterput />} />
        <Route path="flyover/*" element={<Flyover />} />
        <Route path="functioneelgebied/*" element={<Functioneelgebied />} />
        <Route path="geluidsscherm/*" element={<Geluidsscherm />} />
        <Route path="gemaal/*" element={<Gemaal />} />
        <Route path="groenobject/*" element={<Groenobject />} />
        <Route path="infiltratieput/*" element={<Infiltratieput />} />
        <Route path="installatie/*" element={<Installatie />} />
        <Route path="kademuur/*" element={<Kademuur />} />
        <Route path="keermuur/*" element={<Keermuur />} />
        <Route path="klimplant/*" element={<Klimplant />} />
        <Route path="kolk/*" element={<Kolk />} />
        <Route path="kunstwerk/*" element={<Kunstwerk />} />
        <Route path="leiding/*" element={<Leiding />} />
        <Route path="leidingelement/*" element={<Leidingelement />} />
        <Route path="mast/*" element={<Mast />} />
        <Route path="meubilair/*" element={<Meubilair />} />
        <Route path="overbruggingsobject/*" element={<Overbruggingsobject />} />
        <Route path="overstortconstructie/*" element={<Overstortconstructie />} />
        <Route path="paal/*" element={<Paal />} />
        <Route path="pomp/*" element={<Pomp />} />
        <Route path="putdeksel/*" element={<Putdeksel />} />
        <Route path="rioleringsgebied/*" element={<Rioleringsgebied />} />
        <Route path="rioolput/*" element={<Rioolput />} />
        <Route path="scheiding/*" element={<Scheiding />} />
        <Route path="sensor/*" element={<Sensor />} />
        <Route path="solitaireplant/*" element={<Solitaireplant />} />
        <Route path="speelterrein/*" element={<Speelterrein />} />
        <Route path="speeltoestel/*" element={<Speeltoestel />} />
        <Route path="sportterrein/*" element={<Sportterrein />} />
        <Route path="stuwgebied/*" element={<Stuwgebied />} />
        <Route path="terreindeel/*" element={<Terreindeel />} />
        <Route path="tunnelobject/*" element={<Tunnelobject />} />
        <Route path="uitlaatconstructie/*" element={<Uitlaatconstructie />} />
        <Route path="vegetatieobject/*" element={<Vegetatieobject />} />
        <Route path="verhardingsobject/*" element={<Verhardingsobject />} />
        <Route path="verkeersdrempel/*" element={<Verkeersdrempel />} />
        <Route path="verlichtingsobject/*" element={<Verlichtingsobject />} />
        <Route path="viaduct/*" element={<Viaduct />} />
        <Route path="waterinrichtingsobject/*" element={<Waterinrichtingsobject />} />
        <Route path="waterobject/*" element={<Waterobject />} />
        <Route path="weginrichtingsobject/*" element={<Weginrichtingsobject />} />
        <Route path="actie/*" element={<Actie />} />
        <Route path="areaal/*" element={<Areaal />} />
        <Route path="crowmelding/*" element={<Crowmelding />} />
        <Route path="deelplanveld/*" element={<Deelplanveld />} />
        <Route path="faseoplevering/*" element={<Faseoplevering />} />
        <Route path="geoobject/*" element={<Geoobject />} />
        <Route path="grondbeheerder/*" element={<Grondbeheerder />} />
        <Route path="kadastralemutatie/*" element={<Kadastralemutatie />} />
        <Route path="kwaliteitscatalogusopenbareruimte/*" element={<Kwaliteitscatalogusopenbareruimte />} />
        <Route path="kwaliteitskenmerken/*" element={<Kwaliteitskenmerken />} />
        <Route path="elogboek/*" element={<Elogboek />} />
        <Route path="meldingongeval/*" element={<Meldingongeval />} />
        <Route path="moormelding/*" element={<Moormelding />} />
        <Route path="omgevingsvergunning/*" element={<Omgevingsvergunning />} />
        <Route path="onderhoud/*" element={<Onderhoud />} />
        <Route path="opbreking/*" element={<Opbreking />} />
        <Route path="procesverbaalmoormelding/*" element={<Procesverbaalmoormelding />} />
        <Route path="schouwronde/*" element={<Schouwronde />} />
        <Route path="storing/*" element={<Storing />} />
        <Route path="taak/*" element={<Taak />} />
        <Route path="uitvoerdergraafwerkzaamheden/*" element={<Uitvoerdergraafwerkzaamheden />} />
        <Route path="verkeerslicht/*" element={<Verkeerslicht />} />
        <Route path="gebouw/*" element={<Gebouw />} />
        <Route path="huurwoningen/*" element={<Huurwoningen />} />
        <Route path="koopwoningen/*" element={<Koopwoningen />} />
        <Route path="plan/*" element={<Plan />} />
        <Route path="projectleider/*" element={<Projectleider />} />
        <Route path="projectontwikkelaar/*" element={<Projectontwikkelaar />} />
        <Route path="studentenwoningen/*" element={<Studentenwoningen />} />
        <Route path="bevoegdgezag/*" element={<Bevoegdgezag />} />
        <Route path="gemachtigde/*" element={<Gemachtigde />} />
        <Route path="initiatiefnemer/*" element={<Initiatiefnemer />} />
        <Route path="projectactiviteit/*" element={<Projectactiviteit />} />
        <Route path="projectlocatie/*" element={<Projectlocatie />} />
        <Route path="specificatie/*" element={<Specificatie />} />
        <Route path="uitvoerendeinstantie/*" element={<Uitvoerendeinstantie />} />
        <Route path="verzoek/*" element={<Verzoek />} />
        <Route path="omgevingsdocument/*" element={<Omgevingsdocument />} />
        <Route path="regeltekst/*" element={<Regeltekst />} />
        <Route path="beperkingsgebied/*" element={<Beperkingsgebied />} />
        <Route path="functie/*" element={<Functie />} />
        <Route path="gebiedsaanwijzing/*" element={<Gebiedsaanwijzing />} />
        <Route path="idealisatie/*" element={<Idealisatie />} />
        <Route path="instructieregel/*" element={<Instructieregel />} />
        <Route path="juridischeregel/*" element={<Juridischeregel />} />
        <Route path="norm/*" element={<Norm />} />
        <Route path="normwaarde/*" element={<Normwaarde />} />
        <Route path="omgevingsnorm/*" element={<Omgevingsnorm />} />
        <Route path="omgevingswaarde/*" element={<Omgevingswaarde />} />
        <Route path="omgevingswaarderegel/*" element={<Omgevingswaarderegel />} />
        <Route path="regelvooriedereen/*" element={<Regelvooriedereen />} />
        <Route path="thema/*" element={<Thema />} />
        <Route path="conclusie/*" element={<Conclusie />} />
        <Route path="indieningsvereisten/*" element={<Indieningsvereisten />} />
        <Route path="maatregelen/*" element={<Maatregelen />} />
        <Route path="toepasbareregel/*" element={<Toepasbareregel />} />
        <Route path="toepasbareregelbestand/*" element={<Toepasbareregelbestand />} />
        <Route path="uitvoeringsregel/*" element={<Uitvoeringsregel />} />
        <Route path="applicatie/*" element={<Applicatie />} />
        <Route path="attribuutsoort/*" element={<Attribuutsoort />} />
        <Route path="classificatie/*" element={<Classificatie />} />
        <Route path="cmdbitem/*" element={<Cmdbitem />} />
        <Route path="database/*" element={<Database />} />
        <Route path="datatype/*" element={<Datatype />} />
        <Route path="dienst/*" element={<Dienst />} />
        <Route path="domeinoftaakveld/*" element={<Domeinoftaakveld />} />
        <Route path="externebron/*" element={<Externebron />} />
        <Route path="gebruikerrol/*" element={<Gebruikerrol />} />
        <Route path="gegeven/*" element={<Gegeven />} />
        <Route path="generalisatie/*" element={<Generalisatie />} />
        <Route path="hardware/*" element={<Hardware />} />
        <Route path="inventaris/*" element={<Inventaris />} />
        <Route path="koppeling/*" element={<Koppeling />} />
        <Route path="licentie/*" element={<Licentie />} />
        <Route path="linkbaarcmdbitem/*" element={<Linkbaarcmdbitem />} />
        <Route path="elog/*" element={<Elog />} />
        <Route path="nertwerkcomponent/*" element={<Nertwerkcomponent />} />
        <Route path="notitie/*" element={<Notitie />} />
        <Route path="eobjecttype/*" element={<Eobjecttype />} />
        <Route path="onderwerp/*" element={<Onderwerp />} />
        <Route path="epackage/*" element={<Epackage />} />
        <Route path="prijzenboek/*" element={<Prijzenboek />} />
        <Route path="server/*" element={<Server />} />
        <Route path="software/*" element={<Software />} />
        <Route path="telefoniegegevens/*" element={<Telefoniegegevens />} />
        <Route path="toegangsmiddel/*" element={<Toegangsmiddel />} />
        <Route path="versie/*" element={<Versie />} />
        <Route path="vervoersmiddel/*" element={<Vervoersmiddel />} />
        <Route path="wijzigingsverzoek/*" element={<Wijzigingsverzoek />} />
        <Route path="betaalmoment/*" element={<Betaalmoment />} />
        <Route path="rapportagemoment/*" element={<Rapportagemoment />} />
        <Route path="sector/*" element={<Sector />} />
        <Route path="subsidie/*" element={<Subsidie />} />
        <Route path="subsidieaanvraag/*" element={<Subsidieaanvraag />} />
        <Route path="subsidiebeschikking/*" element={<Subsidiebeschikking />} />
        <Route path="subsidiecomponent/*" element={<Subsidiecomponent />} />
        <Route path="subsidieprogramma/*" element={<Subsidieprogramma />} />
        <Route path="aanbestedingvastgoed/*" element={<Aanbestedingvastgoed />} />
        <Route path="adresaanduiding/*" element={<Adresaanduiding />} />
        <Route path="bouwdeel/*" element={<Bouwdeel />} />
        <Route path="bouwdeelelement/*" element={<Bouwdeelelement />} />
        <Route path="cultuuronbebouwd/*" element={<Cultuuronbebouwd />} />
        <Route path="eigenaar/*" element={<Eigenaar />} />
        <Route path="gebruiksdoel/*" element={<Gebruiksdoel />} />
        <Route path="huurder/*" element={<Huurder />} />
        <Route path="kpbetrokkenbij/*" element={<Kpbetrokkenbij />} />
        <Route path="kponstaanuit/*" element={<Kponstaanuit />} />
        <Route path="locatieaanduidingwozobject/*" element={<Locatieaanduidingwozobject />} />
        <Route path="locatieonroerendezaak/*" element={<Locatieonroerendezaak />} />
        <Route path="mjop/*" element={<Mjop />} />
        <Route path="mjopitem/*" element={<Mjopitem />} />
        <Route path="nadaanvullingbrp/*" element={<Nadaanvullingbrp />} />
        <Route path="eobjectrelatie/*" element={<Eobjectrelatie />} />
        <Route path="offerte/*" element={<Offerte />} />
        <Route path="pachter/*" element={<Pachter />} />
        <Route path="prijzenboekitem/*" element={<Prijzenboekitem />} />
        <Route path="vastgoedcontract/*" element={<Vastgoedcontract />} />
        <Route path="vastgoedcontractregel/*" element={<Vastgoedcontractregel />} />
        <Route path="vastgoedobject/*" element={<Vastgoedobject />} />
        <Route path="verhuurbaareenheid/*" element={<Verhuurbaareenheid />} />
        <Route path="werkbon/*" element={<Werkbon />} />
        <Route path="wozbelang/*" element={<Wozbelang />} />
        <Route path="zakelijkrecht/*" element={<Zakelijkrecht />} />
        <Route path="bedrijf/*" element={<Bedrijf />} />
        <Route path="activa/*" element={<Activa />} />
        <Route path="activasoort/*" element={<Activasoort />} />
        <Route path="bankafschrift/*" element={<Bankafschrift />} />
        <Route path="bankafschriftregel/*" element={<Bankafschriftregel />} />
        <Route path="bankrekening/*" element={<Bankrekening />} />
        <Route path="batch/*" element={<Batch />} />
        <Route path="batchregel/*" element={<Batchregel />} />
        <Route path="begroting/*" element={<Begroting />} />
        <Route path="begrotingregel/*" element={<Begrotingregel />} />
        <Route path="debiteur/*" element={<Debiteur />} />
        <Route path="factuur/*" element={<Factuur />} />
        <Route path="factuurregel/*" element={<Factuurregel />} />
        <Route path="hoofdrekening/*" element={<Hoofdrekening />} />
        <Route path="hoofdstuk/*" element={<Hoofdstuk />} />
        <Route path="inkooporder/*" element={<Inkooporder />} />
        <Route path="kostenplaats/*" element={<Kostenplaats />} />
        <Route path="mutatie/*" element={<Mutatie />} />
        <Route path="opdrachtgever/*" element={<Opdrachtgever />} />
        <Route path="opdrachtnemer/*" element={<Opdrachtnemer />} />
        <Route path="subrekening/*" element={<Subrekening />} />
        <Route path="werkorder/*" element={<Werkorder />} />
        <Route path="beoordeling/*" element={<Beoordeling />} />
        <Route path="declaratiesoort/*" element={<Declaratiesoort />} />
        <Route path="dienstverband/*" element={<Dienstverband />} />
        <Route path="disciplinairemaatregel/*" element={<Disciplinairemaatregel />} />
        <Route path="formatieplaats/*" element={<Formatieplaats />} />
        <Route path="functiehuis/*" element={<Functiehuis />} />
        <Route path="genotenopleiding/*" element={<Genotenopleiding />} />
        <Route path="geweldsincident/*" element={<Geweldsincident />} />
        <Route path="individueelkeuzebudget/*" element={<Individueelkeuzebudget />} />
        <Route path="inzet/*" element={<Inzet />} />
        <Route path="keuzebudgetbesteding/*" element={<Keuzebudgetbesteding />} />
        <Route path="keuzebudgetbestedingsoort/*" element={<Keuzebudgetbestedingsoort />} />
        <Route path="normprofiel/*" element={<Normprofiel />} />
        <Route path="onderwijsinstituut/*" element={<Onderwijsinstituut />} />
        <Route path="opleiding/*" element={<Opleiding />} />
        <Route path="organisatorischeeenheidhr/*" element={<Organisatorischeeenheidhr />} />
        <Route path="sollicitant/*" element={<Sollicitant />} />
        <Route path="sollicitatie/*" element={<Sollicitatie />} />
        <Route path="sollicitatiegesprek/*" element={<Sollicitatiegesprek />} />
        <Route path="soortdisciplinairemaatregel/*" element={<Soortdisciplinairemaatregel />} />
        <Route path="uren/*" element={<Uren />} />
        <Route path="vacature/*" element={<Vacature />} />
        <Route path="verlof/*" element={<Verlof />} />
        <Route path="verlofsoort/*" element={<Verlofsoort />} />
        <Route path="verzuim/*" element={<Verzuim />} />
        <Route path="verzuimsoort/*" element={<Verzuimsoort />} />
        <Route path="werknemer/*" element={<Werknemer />} />
        <Route path="aanbesteding/*" element={<Aanbesteding />} />
        <Route path="aanbestedinginhuur/*" element={<Aanbestedinginhuur />} />
        <Route path="aankondiging/*" element={<Aankondiging />} />
        <Route path="aanvraaginkooporder/*" element={<Aanvraaginkooporder />} />
        <Route path="contract/*" element={<Contract />} />
        <Route path="cpvcode/*" element={<Cpvcode />} />
        <Route path="formulierinhuur/*" element={<Formulierinhuur />} />
        <Route path="formulierverlenginginhuur/*" element={<Formulierverlenginginhuur />} />
        <Route path="gunning/*" element={<Gunning />} />
        <Route path="inkooppakket/*" element={<Inkooppakket />} />
        <Route path="kandidaat/*" element={<Kandidaat />} />
        <Route path="kwalificatie/*" element={<Kwalificatie />} />
        <Route path="offerteaanvraag/*" element={<Offerteaanvraag />} />
        <Route path="selectietabelaanbesteding/*" element={<Selectietabelaanbesteding />} />
        <Route path="startformulieraanbesteden/*" element={<Startformulieraanbesteden />} />
        <Route path="uitnodiging/*" element={<Uitnodiging />} />
        <Route path="aanvraagdata/*" element={<Aanvraagdata />} />
        <Route path="afspraakstatus/*" element={<Afspraakstatus />} />
        <Route path="artikel/*" element={<Artikel />} />
        <Route path="balieafspraak/*" element={<Balieafspraak />} />
        <Route path="formuliersoort/*" element={<Formuliersoort />} />
        <Route path="formuliersoortveld/*" element={<Formuliersoortveld />} />
        <Route path="klantbeoordeling/*" element={<Klantbeoordeling />} />
        <Route path="klantbeoordelingreden/*" element={<Klantbeoordelingreden />} />
        <Route path="productofdienst/*" element={<Productofdienst />} />
        <Route path="telefoononderwerp/*" element={<Telefoononderwerp />} />
        <Route path="telefoonstatus/*" element={<Telefoonstatus />} />
        <Route path="telefoontje/*" element={<Telefoontje />} />
        <Route path="nummeraanduiding/*" element={<Nummeraanduiding />} />
        <Route path="buurt/*" element={<Buurt />} />
        <Route path="gemeente/*" element={<Gemeente />} />
        <Route path="ligplaats/*" element={<Ligplaats />} />
        <Route path="adresseerbaarobject/*" element={<Adresseerbaarobject />} />
        <Route path="openbareruimte/*" element={<Openbareruimte />} />
        <Route path="pand/*" element={<Pand />} />
        <Route path="verblijfsobject/*" element={<Verblijfsobject />} />
        <Route path="wijk/*" element={<Wijk />} />
        <Route path="woonplaats/*" element={<Woonplaats />} />
        <Route path="periode/*" element={<Periode />} />
        <Route path="foto/*" element={<Foto />} />
        <Route path="gebied/*" element={<Gebied />} />
        <Route path="gebiedengroep/*" element={<Gebiedengroep />} />
        <Route path="lijn/*" element={<Lijn />} />
        <Route path="lijnengroep/*" element={<Lijnengroep />} />
        <Route path="punt/*" element={<Punt />} />
        <Route path="puntengroep/*" element={<Puntengroep />} />
        <Route path="videoopname/*" element={<Videoopname />} />
        <Route path="bedrijfsproces/*" element={<Bedrijfsproces />} />
        <Route path="bedrijfsprocestype/*" element={<Bedrijfsprocestype />} />
        <Route path="besluit/*" element={<Besluit />} />
        <Route path="besluittype/*" element={<Besluittype />} />
        <Route path="betaling/*" element={<Betaling />} />
        <Route path="betrokkene/*" element={<Betrokkene />} />
        <Route path="deelproces/*" element={<Deelproces />} />
        <Route path="deelprocestype/*" element={<Deelprocestype />} />
        <Route path="document/*" element={<Document />} />
        <Route path="documenttype/*" element={<Documenttype />} />
        <Route path="enkelvoudigdocument/*" element={<Enkelvoudigdocument />} />
        <Route path="heffing/*" element={<Heffing />} />
        <Route path="identificatiekenmerk/*" element={<Identificatiekenmerk />} />
        <Route path="klantcontact/*" element={<Klantcontact />} />
        <Route path="medewerker/*" element={<Medewerker />} />
        <Route path="eobject/*" element={<Eobject />} />
        <Route path="organisatorischeeenheid/*" element={<Organisatorischeeenheid />} />
        <Route path="samengestelddocument/*" element={<Samengestelddocument />} />
        <Route path="status/*" element={<Status />} />
        <Route path="statustype/*" element={<Statustype />} />
        <Route path="vestigingvanzaakbehandelendeorganisatie/*" element={<Vestigingvanzaakbehandelendeorganisatie />} />
        <Route path="zaak/*" element={<Zaak />} />
        <Route path="zaakorigineel/*" element={<Zaakorigineel />} />
        <Route path="zaaktype/*" element={<Zaaktype />} />
        <Route path="afwijkendbuitenlandscorrespondentieadresrol/*" element={<Afwijkendbuitenlandscorrespondentieadresrol />} />
        <Route path="afwijkendcorrespondentiepostadresrol/*" element={<Afwijkendcorrespondentiepostadresrol />} />
        <Route path="anderzaakobjectzaak/*" element={<Anderzaakobjectzaak />} />
        <Route path="contactpersoonrol/*" element={<Contactpersoonrol />} />
        <Route path="kenmerkenzaak/*" element={<Kenmerkenzaak />} />
        <Route path="opschortingzaak/*" element={<Opschortingzaak />} />
        <Route path="verlengingzaak/*" element={<Verlengingzaak />} />
        <Route path="brondocumenten/*" element={<Brondocumenten />} />
        <Route path="formelehistorie/*" element={<Formelehistorie />} />
        <Route path="inonderzoek/*" element={<Inonderzoek />} />
        <Route path="materielehistorie/*" element={<Materielehistorie />} />
        <Route path="strijdigheidofnietigheid/*" element={<Strijdigheidofnietigheid />} />
        <Route path="aantekening/*" element={<Aantekening />} />
        <Route path="adresbuitenland/*" element={<Adresbuitenland />} />
        <Route path="briefadres/*" element={<Briefadres />} />
        <Route path="nationaliteit/*" element={<Nationaliteit />} />
        <Route path="onbestemdadres/*" element={<Onbestemdadres />} />
        <Route path="appartementsrecht/*" element={<Appartementsrecht />} />
        <Route path="appartementsrechtsplitsing/*" element={<Appartementsrechtsplitsing />} />
        <Route path="begroeidterreindeel/*" element={<Begroeidterreindeel />} />
        <Route path="benoemdobject/*" element={<Benoemdobject />} />
        <Route path="benoemdterrein/*" element={<Benoemdterrein />} />
        <Route path="gebouwdobject/*" element={<Gebouwdobject />} />
        <Route path="gebouwinstallatie/*" element={<Gebouwinstallatie />} />
        <Route path="inrichtingselement/*" element={<Inrichtingselement />} />
        <Route path="kadastraalperceel/*" element={<Kadastraalperceel />} />
        <Route path="kadastraleonroerendezaak/*" element={<Kadastraleonroerendezaak />} />
        <Route path="ingeschrevenpersoon/*" element={<Ingeschrevenpersoon />} />
        <Route path="kadastraleonroerendezaakaantekening/*" element={<Kadastraleonroerendezaakaantekening />} />
        <Route path="ingezetene/*" element={<Ingezetene />} />
        <Route path="kunstwerkdeel/*" element={<Kunstwerkdeel />} />
        <Route path="maatschappelijkeactiviteit/*" element={<Maatschappelijkeactiviteit />} />
        <Route path="adresseerbaarobjectaanduiding/*" element={<Adresseerbaarobjectaanduiding />} />
        <Route path="onbegroeidterreindeel/*" element={<Onbegroeidterreindeel />} />
        <Route path="ondersteunendwaterdeel/*" element={<Ondersteunendwaterdeel />} />
        <Route path="ondersteunendwegdeel/*" element={<Ondersteunendwegdeel />} />
        <Route path="overbruggingsdeel/*" element={<Overbruggingsdeel />} />
        <Route path="overigbenoemdterrein/*" element={<Overigbenoemdterrein />} />
        <Route path="natuurlijkpersoon/*" element={<Natuurlijkpersoon />} />
        <Route path="overigbouwwerk/*" element={<Overigbouwwerk />} />
        <Route path="overiggebouwdobject/*" element={<Overiggebouwdobject />} />
        <Route path="nietnatuurlijkpersoon/*" element={<Nietnatuurlijkpersoon />} />
        <Route path="overigeadresseerbaarobjectaanduiding/*" element={<Overigeadresseerbaarobjectaanduiding />} />
        <Route path="overigescheiding/*" element={<Overigescheiding />} />
        <Route path="reisdocument/*" element={<Reisdocument />} />
        <Route path="rechtspersoon/*" element={<Rechtspersoon />} />
        <Route path="tenaamstelling/*" element={<Tenaamstelling />} />
        <Route path="tunneldeel/*" element={<Tunneldeel />} />
        <Route path="vestiging/*" element={<Vestiging />} />
        <Route path="waterdeel/*" element={<Waterdeel />} />
        <Route path="wegdeel/*" element={<Wegdeel />} />
        <Route path="verblijfstitel/*" element={<Verblijfstitel />} />
        <Route path="wozdeelobject/*" element={<Wozdeelobject />} />
        <Route path="wozobject/*" element={<Wozobject />} />
        <Route path="wozwaarde/*" element={<Wozwaarde />} />
        <Route path="zekerheidsrecht/*" element={<Zekerheidsrecht />} />
        <Route path="locatieaanduidingadreswozobject/*" element={<Locatieaanduidingadreswozobject />} />
        <Route path="correspondentieadresbuitenland/*" element={<Correspondentieadresbuitenland />} />
        <Route path="geboorteingeschrevennatuurlijkpersoon/*" element={<Geboorteingeschrevennatuurlijkpersoon />} />
        <Route path="geboorteingeschrevenpersoon/*" element={<Geboorteingeschrevenpersoon />} />
        <Route path="handelsnamenmaatschappelijkeactiviteit/*" element={<Handelsnamenmaatschappelijkeactiviteit />} />
        <Route path="handelsnamenvestiging/*" element={<Handelsnamenvestiging />} />
        <Route path="koopsomkadastraleonroerendezaak/*" element={<Koopsomkadastraleonroerendezaak />} />
        <Route path="locatiekadastraleonroerendezaak/*" element={<Locatiekadastraleonroerendezaak />} />
        <Route path="migratieingeschrevennatuurlijkpersoon/*" element={<Migratieingeschrevennatuurlijkpersoon />} />
        <Route path="naamaanschrijvingnatuurlijkpersoon/*" element={<Naamaanschrijvingnatuurlijkpersoon />} />
        <Route path="naamgebruiknatuurlijkpersoon/*" element={<Naamgebruiknatuurlijkpersoon />} />
        <Route path="naamnatuurlijkpersoon/*" element={<Naamnatuurlijkpersoon />} />
        <Route path="nationaliteitingeschrevennatuurlijkpersoon/*" element={<Nationaliteitingeschrevennatuurlijkpersoon />} />
        <Route path="nederlandsenationaliteitingeschrevenpersoon/*" element={<Nederlandsenationaliteitingeschrevenpersoon />} />
        <Route path="ontbindinghuwelijkgeregistreerdpartnerschap/*" element={<Ontbindinghuwelijkgeregistreerdpartnerschap />} />
        <Route path="overlijdeningeschrevennatuurlijkpersoon/*" element={<Overlijdeningeschrevennatuurlijkpersoon />} />
        <Route path="overlijdeningeschrevenpersoon/*" element={<Overlijdeningeschrevenpersoon />} />
        <Route path="postadres/*" element={<Postadres />} />
        <Route path="rekeningnummer/*" element={<Rekeningnummer />} />
        <Route path="samengesteldenaamnatuurlijkpersoon/*" element={<Samengesteldenaamnatuurlijkpersoon />} />
        <Route path="sbiactiviteitvestiging/*" element={<Sbiactiviteitvestiging />} />
        <Route
          path="sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/*"
          element={<Sluitingofaangaanhuwelijkofgeregistreerdpartnerschap />}
        />
        <Route path="soortfunctioneelgebied/*" element={<Soortfunctioneelgebied />} />
        <Route path="soortkunstwerk/*" element={<Soortkunstwerk />} />
        <Route path="soortoverigbouwwerk/*" element={<Soortoverigbouwwerk />} />
        <Route path="soortscheiding/*" element={<Soortscheiding />} />
        <Route path="soortspoor/*" element={<Soortspoor />} />
        <Route path="splitsingstekeningreferentie/*" element={<Splitsingstekeningreferentie />} />
        <Route path="verblijfadresingeschrevennatuurlijkpersoon/*" element={<Verblijfadresingeschrevennatuurlijkpersoon />} />
        <Route path="verblijfadresingeschrevenpersoon/*" element={<Verblijfadresingeschrevenpersoon />} />
        <Route path="verblijfbuitenland/*" element={<Verblijfbuitenland />} />
        <Route path="verblijfbuitenlandsubject/*" element={<Verblijfbuitenlandsubject />} />
        <Route path="verblijfsrechtingeschrevennatuurlijkpersoon/*" element={<Verblijfsrechtingeschrevennatuurlijkpersoon />} />
        <Route
          path="verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon/*"
          element={<Verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon />}
        />
        <Route path="aanduidingverblijfsrecht/*" element={<Aanduidingverblijfsrecht />} />
        <Route path="autoriteitafgiftenederlandsreisdocument/*" element={<Autoriteitafgiftenederlandsreisdocument />} />
        <Route path="aardaantekening/*" element={<Aardaantekening />} />
        <Route path="aardzakelijkrecht/*" element={<Aardzakelijkrecht />} />
        <Route path="aardfiliatie/*" element={<Aardfiliatie />} />
        <Route path="academischetitel/*" element={<Academischetitel />} />
        <Route path="akrkadastralegemeentecode/*" element={<Akrkadastralegemeentecode />} />
        <Route path="cultuurcodebebouwd/*" element={<Cultuurcodebebouwd />} />
        <Route path="cultuurcodeonbebouwd/*" element={<Cultuurcodeonbebouwd />} />
        <Route path="wozdeelobjectcode/*" element={<Wozdeelobjectcode />} />
        <Route path="kadastralegemeente/*" element={<Kadastralegemeente />} />
        <Route path="landofgebied/*" element={<Landofgebied />} />
        <Route path="provincie/*" element={<Provincie />} />
        <Route path="partij/*" element={<Partij />} />
        <Route path="redenverkrijgingnationaliteit/*" element={<Redenverkrijgingnationaliteit />} />
        <Route path="redenverliesnationaliteit/*" element={<Redenverliesnationaliteit />} />
        <Route path="reisdocumentsoort/*" element={<Reisdocumentsoort />} />
        <Route path="sbiactiviteit/*" element={<Sbiactiviteit />} />
        <Route path="soortgrootte/*" element={<Soortgrootte />} />
        <Route path="soortwozobject/*" element={<Soortwozobject />} />
        <Route path="valutasoort/*" element={<Valutasoort />} />
        <Route path="land/*" element={<Land />} />
        <Route path="valuta/*" element={<Valuta />} />
        <Route path="eobjecttypea/*" element={<Eobjecttypea />} />
        <Route path="eobjecttypeb/*" element={<Eobjecttypeb />} />
        <Route path="eobjecttypec/*" element={<Eobjecttypec />} />
        <Route path="eobjecttyped/*" element={<Eobjecttyped />} />
        <Route path="eobjecttypee/*" element={<Eobjecttypee />} />
        <Route path="eobjecttypef/*" element={<Eobjecttypef />} />
        <Route path="eobjecttypeg/*" element={<Eobjecttypeg />} />
        <Route path="childclassa/*" element={<Childclassa />} />
        <Route path="classa/*" element={<Classa />} />
        <Route path="classb/*" element={<Classb />} />
        <Route path="classc/*" element={<Classc />} />
        <Route path="class-1/*" element={<Class1 />} />
        <Route path="classd/*" element={<Classd />} />
        <Route path="classe/*" element={<Classe />} />
        <Route path="classf/*" element={<Classf />} />
        <Route path="classg/*" element={<Classg />} />
        <Route path="classh/*" element={<Classh />} />
        <Route path="classi/*" element={<Classi />} />
        <Route path="classj/*" element={<Classj />} />
        <Route path="kpclassaclassc/*" element={<Kpclassaclassc />} />
        <Route path="enumenumerationa/*" element={<Enumenumerationa />} />
        <Route path="lstclass-1/*" element={<Lstclass1 />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
