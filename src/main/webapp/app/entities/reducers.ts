import aanwezigedeelnemer from 'app/entities/aanwezigedeelnemer/aanwezigedeelnemer.reducer';
import agendapunt from 'app/entities/agendapunt/agendapunt.reducer';
import categorie from 'app/entities/categorie/categorie.reducer';
import collegelid from 'app/entities/collegelid/collegelid.reducer';
import dossier from 'app/entities/dossier/dossier.reducer';
import indiener from 'app/entities/indiener/indiener.reducer';
import programma from 'app/entities/programma/programma.reducer';
import raadscommissie from 'app/entities/raadscommissie/raadscommissie.reducer';
import raadslid from 'app/entities/raadslid/raadslid.reducer';
import raadsstuk from 'app/entities/raadsstuk/raadsstuk.reducer';
import stemming from 'app/entities/stemming/stemming.reducer';
import taakveld from 'app/entities/taakveld/taakveld.reducer';
import vergadering from 'app/entities/vergadering/vergadering.reducer';
import activiteit from 'app/entities/activiteit/activiteit.reducer';
import aomstatus from 'app/entities/aomstatus/aomstatus.reducer';
import bevinding from 'app/entities/bevinding/bevinding.reducer';
import boa from 'app/entities/boa/boa.reducer';
import combibon from 'app/entities/combibon/combibon.reducer';
import fietsregistratie from 'app/entities/fietsregistratie/fietsregistratie.reducer';
import grondslag from 'app/entities/grondslag/grondslag.reducer';
import heffinggrondslag from 'app/entities/heffinggrondslag/heffinggrondslag.reducer';
import heffingsverordening from 'app/entities/heffingsverordening/heffingsverordening.reducer';
import inspectie from 'app/entities/inspectie/inspectie.reducer';
import kosten from 'app/entities/kosten/kosten.reducer';
import legesgrondslag from 'app/entities/legesgrondslag/legesgrondslag.reducer';
import ligplaatsontheffing from 'app/entities/ligplaatsontheffing/ligplaatsontheffing.reducer';
import moraanvraagofmelding from 'app/entities/moraanvraagofmelding/moraanvraagofmelding.reducer';
import openbareactiviteit from 'app/entities/openbareactiviteit/openbareactiviteit.reducer';
import precario from 'app/entities/precario/precario.reducer';
import producttype from 'app/entities/producttype/producttype.reducer';
import subproducttype from 'app/entities/subproducttype/subproducttype.reducer';
import vaartuig from 'app/entities/vaartuig/vaartuig.reducer';
import vomaanvraagofmelding from 'app/entities/vomaanvraagofmelding/vomaanvraagofmelding.reducer';
import vordering from 'app/entities/vordering/vordering.reducer';
import vorderingregel from 'app/entities/vorderingregel/vorderingregel.reducer';
import vthaanvraagofmelding from 'app/entities/vthaanvraagofmelding/vthaanvraagofmelding.reducer';
import vthmelding from 'app/entities/vthmelding/vthmelding.reducer';
import vthzaak from 'app/entities/vthzaak/vthzaak.reducer';
import waarneming from 'app/entities/waarneming/waarneming.reducer';
import waboaanvraagofmelding from 'app/entities/waboaanvraagofmelding/waboaanvraagofmelding.reducer';
import woonfraudeaanvraagofmelding from 'app/entities/woonfraudeaanvraagofmelding/woonfraudeaanvraagofmelding.reducer';
import woonoverlastaanvraagofmelding from 'app/entities/woonoverlastaanvraagofmelding/woonoverlastaanvraagofmelding.reducer';
import belprovider from 'app/entities/belprovider/belprovider.reducer';
import mulderfeit from 'app/entities/mulderfeit/mulderfeit.reducer';
import naheffing from 'app/entities/naheffing/naheffing.reducer';
import parkeergarage from 'app/entities/parkeergarage/parkeergarage.reducer';
import parkeerrecht from 'app/entities/parkeerrecht/parkeerrecht.reducer';
import parkeerscan from 'app/entities/parkeerscan/parkeerscan.reducer';
import parkeervergunning from 'app/entities/parkeervergunning/parkeervergunning.reducer';
import parkeervlak from 'app/entities/parkeervlak/parkeervlak.reducer';
import parkeerzone from 'app/entities/parkeerzone/parkeerzone.reducer';
import productgroep from 'app/entities/productgroep/productgroep.reducer';
import productsoort from 'app/entities/productsoort/productsoort.reducer';
import straatsectie from 'app/entities/straatsectie/straatsectie.reducer';
import voertuig from 'app/entities/voertuig/voertuig.reducer';
import stremming from 'app/entities/stremming/stremming.reducer';
import strooidag from 'app/entities/strooidag/strooidag.reducer';
import strooiroute from 'app/entities/strooiroute/strooiroute.reducer';
import strooirouteuitvoering from 'app/entities/strooirouteuitvoering/strooirouteuitvoering.reducer';
import verkeersbesluit from 'app/entities/verkeersbesluit/verkeersbesluit.reducer';
import verkeerstelling from 'app/entities/verkeerstelling/verkeerstelling.reducer';
import vloginfo from 'app/entities/vloginfo/vloginfo.reducer';
import contact from 'app/entities/contact/contact.reducer';
import hotel from 'app/entities/hotel/hotel.reducer';
import hotelbezoek from 'app/entities/hotelbezoek/hotelbezoek.reducer';
import verkooppunt from 'app/entities/verkooppunt/verkooppunt.reducer';
import werkgelegenheid from 'app/entities/werkgelegenheid/werkgelegenheid.reducer';
import winkelvloeroppervlak from 'app/entities/winkelvloeroppervlak/winkelvloeroppervlak.reducer';
import aanvraagleerlingenvervoer from 'app/entities/aanvraagleerlingenvervoer/aanvraagleerlingenvervoer.reducer';
import aanvraagvrijstelling from 'app/entities/aanvraagvrijstelling/aanvraagvrijstelling.reducer';
import beschikkingleerlingenvervoer from 'app/entities/beschikkingleerlingenvervoer/beschikkingleerlingenvervoer.reducer';
import beslissing from 'app/entities/beslissing/beslissing.reducer';
import doorgeleidingom from 'app/entities/doorgeleidingom/doorgeleidingom.reducer';
import haltverwijzing from 'app/entities/haltverwijzing/haltverwijzing.reducer';
import klachtleerlingenvervoer from 'app/entities/klachtleerlingenvervoer/klachtleerlingenvervoer.reducer';
import leerplichtambtenaar from 'app/entities/leerplichtambtenaar/leerplichtambtenaar.reducer';
import procesverbaalonderwijs from 'app/entities/procesverbaalonderwijs/procesverbaalonderwijs.reducer';
import verlofaanvraag from 'app/entities/verlofaanvraag/verlofaanvraag.reducer';
import vervoerder from 'app/entities/vervoerder/vervoerder.reducer';
import verzuimmelding from 'app/entities/verzuimmelding/verzuimmelding.reducer';
import ziekmeldingleerlingenvervoer from 'app/entities/ziekmeldingleerlingenvervoer/ziekmeldingleerlingenvervoer.reducer';
import inschrijving from 'app/entities/inschrijving/inschrijving.reducer';
import leerjaar from 'app/entities/leerjaar/leerjaar.reducer';
import leerling from 'app/entities/leerling/leerling.reducer';
import locatie from 'app/entities/locatie/locatie.reducer';
import loopbaanstap from 'app/entities/loopbaanstap/loopbaanstap.reducer';
import onderwijsloopbaan from 'app/entities/onderwijsloopbaan/onderwijsloopbaan.reducer';
import onderwijsniveau from 'app/entities/onderwijsniveau/onderwijsniveau.reducer';
import onderwijssoort from 'app/entities/onderwijssoort/onderwijssoort.reducer';
import ouderofverzorger from 'app/entities/ouderofverzorger/ouderofverzorger.reducer';
import school from 'app/entities/school/school.reducer';
import startkwalificatie from 'app/entities/startkwalificatie/startkwalificatie.reducer';
import uitschrijving from 'app/entities/uitschrijving/uitschrijving.reducer';
import archeologiebesluit from 'app/entities/archeologiebesluit/archeologiebesluit.reducer';
import artefact from 'app/entities/artefact/artefact.reducer';
import artefactsoort from 'app/entities/artefactsoort/artefactsoort.reducer';
import boring from 'app/entities/boring/boring.reducer';
import doos from 'app/entities/doos/doos.reducer';
import kaart from 'app/entities/kaart/kaart.reducer';
import magazijnlocatie from 'app/entities/magazijnlocatie/magazijnlocatie.reducer';
import magazijnplaatsing from 'app/entities/magazijnplaatsing/magazijnplaatsing.reducer';
import project from 'app/entities/project/project.reducer';
import put from 'app/entities/put/put.reducer';
import spoor from 'app/entities/spoor/spoor.reducer';
import vindplaats from 'app/entities/vindplaats/vindplaats.reducer';
import vondst from 'app/entities/vondst/vondst.reducer';
import vulling from 'app/entities/vulling/vulling.reducer';
import archief from 'app/entities/archief/archief.reducer';
import archiefcategorie from 'app/entities/archiefcategorie/archiefcategorie.reducer';
import archiefstuk from 'app/entities/archiefstuk/archiefstuk.reducer';
import auteur from 'app/entities/auteur/auteur.reducer';
import bezoeker from 'app/entities/bezoeker/bezoeker.reducer';
import depot from 'app/entities/depot/depot.reducer';
import digitaalbestand from 'app/entities/digitaalbestand/digitaalbestand.reducer';
import indeling from 'app/entities/indeling/indeling.reducer';
import index from 'app/entities/index/index.reducer';
import kast from 'app/entities/kast/kast.reducer';
import naderetoegang from 'app/entities/naderetoegang/naderetoegang.reducer';
import ordeningsschema from 'app/entities/ordeningsschema/ordeningsschema.reducer';
import plank from 'app/entities/plank/plank.reducer';
import rechthebbende from 'app/entities/rechthebbende/rechthebbende.reducer';
import uitgever from 'app/entities/uitgever/uitgever.reducer';
import erfgoedobject from 'app/entities/erfgoedobject/erfgoedobject.reducer';
import historischerol from 'app/entities/historischerol/historischerol.reducer';
import historischpersoon from 'app/entities/historischpersoon/historischpersoon.reducer';
import eobjectclassificatie from 'app/entities/eobjectclassificatie/eobjectclassificatie.reducer';
import ambacht from 'app/entities/ambacht/ambacht.reducer';
import beschermdestatus from 'app/entities/beschermdestatus/beschermdestatus.reducer';
import bouwactiviteit from 'app/entities/bouwactiviteit/bouwactiviteit.reducer';
import bouwstijl from 'app/entities/bouwstijl/bouwstijl.reducer';
import bouwtype from 'app/entities/bouwtype/bouwtype.reducer';
import oorspronkelijkefunctie from 'app/entities/oorspronkelijkefunctie/oorspronkelijkefunctie.reducer';
import activiteitsoort from 'app/entities/activiteitsoort/activiteitsoort.reducer';
import balieverkoop from 'app/entities/balieverkoop/balieverkoop.reducer';
import balieverkoopentreekaart from 'app/entities/balieverkoopentreekaart/balieverkoopentreekaart.reducer';
import belanghebbende from 'app/entities/belanghebbende/belanghebbende.reducer';
import belangtype from 'app/entities/belangtype/belangtype.reducer';
import bruikleen from 'app/entities/bruikleen/bruikleen.reducer';
import collectie from 'app/entities/collectie/collectie.reducer';
import doelgroep from 'app/entities/doelgroep/doelgroep.reducer';
import incident from 'app/entities/incident/incident.reducer';
import lener from 'app/entities/lener/lener.reducer';
import mailing from 'app/entities/mailing/mailing.reducer';
import museumobject from 'app/entities/museumobject/museumobject.reducer';
import museumrelatie from 'app/entities/museumrelatie/museumrelatie.reducer';
import omzetgroep from 'app/entities/omzetgroep/omzetgroep.reducer';
import prijs from 'app/entities/prijs/prijs.reducer';
import product from 'app/entities/product/product.reducer';
import productieeenheid from 'app/entities/productieeenheid/productieeenheid.reducer';
import programmasoort from 'app/entities/programmasoort/programmasoort.reducer';
import reservering from 'app/entities/reservering/reservering.reducer';
import rondleiding from 'app/entities/rondleiding/rondleiding.reducer';
import samensteller from 'app/entities/samensteller/samensteller.reducer';
import standplaats from 'app/entities/standplaats/standplaats.reducer';
import tentoonstelling from 'app/entities/tentoonstelling/tentoonstelling.reducer';
import voorziening from 'app/entities/voorziening/voorziening.reducer';
import winkelverkoopgroep from 'app/entities/winkelverkoopgroep/winkelverkoopgroep.reducer';
import winkelvoorraaditem from 'app/entities/winkelvoorraaditem/winkelvoorraaditem.reducer';
import zaal from 'app/entities/zaal/zaal.reducer';
import aantal from 'app/entities/aantal/aantal.reducer';
import belijning from 'app/entities/belijning/belijning.reducer';
import bezetting from 'app/entities/bezetting/bezetting.reducer';
import binnenlocatie from 'app/entities/binnenlocatie/binnenlocatie.reducer';
import onderhoudskosten from 'app/entities/onderhoudskosten/onderhoudskosten.reducer';
import sport from 'app/entities/sport/sport.reducer';
import sportlocatie from 'app/entities/sportlocatie/sportlocatie.reducer';
import sportmateriaal from 'app/entities/sportmateriaal/sportmateriaal.reducer';
import sportpark from 'app/entities/sportpark/sportpark.reducer';
import sportvereniging from 'app/entities/sportvereniging/sportvereniging.reducer';
import gemeentebegrafenis from 'app/entities/gemeentebegrafenis/gemeentebegrafenis.reducer';
import aanvraagstadspas from 'app/entities/aanvraagstadspas/aanvraagstadspas.reducer';
import aomaanvraagwmojeugd from 'app/entities/aomaanvraagwmojeugd/aomaanvraagwmojeugd.reducer';
import aommeldingwmojeugd from 'app/entities/aommeldingwmojeugd/aommeldingwmojeugd.reducer';
import beperking from 'app/entities/beperking/beperking.reducer';
import beperkingscategorie from 'app/entities/beperkingscategorie/beperkingscategorie.reducer';
import beperkingscore from 'app/entities/beperkingscore/beperkingscore.reducer';
import beperkingscoresoort from 'app/entities/beperkingscoresoort/beperkingscoresoort.reducer';
import beschikking from 'app/entities/beschikking/beschikking.reducer';
import beschikkingsoort from 'app/entities/beschikkingsoort/beschikkingsoort.reducer';
import beschiktevoorziening from 'app/entities/beschiktevoorziening/beschiktevoorziening.reducer';
import budgetuitputting from 'app/entities/budgetuitputting/budgetuitputting.reducer';
import client from 'app/entities/client/client.reducer';
import clientbegeleider from 'app/entities/clientbegeleider/clientbegeleider.reducer';
import declaratie from 'app/entities/declaratie/declaratie.reducer';
import declaratieregel from 'app/entities/declaratieregel/declaratieregel.reducer';
import huishouden from 'app/entities/huishouden/huishouden.reducer';
import leefgebied from 'app/entities/leefgebied/leefgebied.reducer';
import leverancier from 'app/entities/leverancier/leverancier.reducer';
import levering from 'app/entities/levering/levering.reducer';
import leveringsvorm from 'app/entities/leveringsvorm/leveringsvorm.reducer';
import meldingeigenbijdrage from 'app/entities/meldingeigenbijdrage/meldingeigenbijdrage.reducer';
import pgbtoekenning from 'app/entities/pgbtoekenning/pgbtoekenning.reducer';
import relatiesoort from 'app/entities/relatiesoort/relatiesoort.reducer';
import tarief from 'app/entities/tarief/tarief.reducer';
import team from 'app/entities/team/team.reducer';
import toewijzing from 'app/entities/toewijzing/toewijzing.reducer';
import verplichtingwmojeugd from 'app/entities/verplichtingwmojeugd/verplichtingwmojeugd.reducer';
import verzoekomtoewijzing from 'app/entities/verzoekomtoewijzing/verzoekomtoewijzing.reducer';
import voorzieningsoort from 'app/entities/voorzieningsoort/voorzieningsoort.reducer';
import zelfredzaamheidmatrix from 'app/entities/zelfredzaamheidmatrix/zelfredzaamheidmatrix.reducer';
import asielstatushouder from 'app/entities/asielstatushouder/asielstatushouder.reducer';
import b1 from 'app/entities/b-1/b-1.reducer';
import bredeintake from 'app/entities/bredeintake/bredeintake.reducer';
import examen from 'app/entities/examen/examen.reducer';
import examenonderdeel from 'app/entities/examenonderdeel/examenonderdeel.reducer';
import gezinsmigrantenoverigemigrant from 'app/entities/gezinsmigrantenoverigemigrant/gezinsmigrantenoverigemigrant.reducer';
import inburgeraar from 'app/entities/inburgeraar/inburgeraar.reducer';
import inburgeringstraject from 'app/entities/inburgeringstraject/inburgeringstraject.reducer';
import knm from 'app/entities/knm/knm.reducer';
import leerroute from 'app/entities/leerroute/leerroute.reducer';
import map from 'app/entities/map/map.reducer';
import participatiecomponent from 'app/entities/participatiecomponent/participatiecomponent.reducer';
import pip from 'app/entities/pip/pip.reducer';
import pvt from 'app/entities/pvt/pvt.reducer';
import verblijfplaats from 'app/entities/verblijfplaats/verblijfplaats.reducer';
import verblijfplaatsazc from 'app/entities/verblijfplaatsazc/verblijfplaatsazc.reducer';
import vreemdeling from 'app/entities/vreemdeling/vreemdeling.reducer';
import z from 'app/entities/z/z.reducer';
import financielesituatie from 'app/entities/financielesituatie/financielesituatie.reducer';
import notarielestatus from 'app/entities/notarielestatus/notarielestatus.reducer';
import projectsoort from 'app/entities/projectsoort/projectsoort.reducer';
import resultaat from 'app/entities/resultaat/resultaat.reducer';
import resultaatsoort from 'app/entities/resultaatsoort/resultaatsoort.reducer';
import uitstroomreden from 'app/entities/uitstroomreden/uitstroomreden.reducer';
import uitstroomredensoort from 'app/entities/uitstroomredensoort/uitstroomredensoort.reducer';
import behandeling from 'app/entities/behandeling/behandeling.reducer';
import behandelsoort from 'app/entities/behandelsoort/behandelsoort.reducer';
import bijzonderheid from 'app/entities/bijzonderheid/bijzonderheid.reducer';
import bijzonderheidsoort from 'app/entities/bijzonderheidsoort/bijzonderheidsoort.reducer';
import caseaanmelding from 'app/entities/caseaanmelding/caseaanmelding.reducer';
import doelstelling from 'app/entities/doelstelling/doelstelling.reducer';
import doelstellingsoort from 'app/entities/doelstellingsoort/doelstellingsoort.reducer';
import sociaalteamdossier from 'app/entities/sociaalteamdossier/sociaalteamdossier.reducer';
import sociaalteamdossiersoort from 'app/entities/sociaalteamdossiersoort/sociaalteamdossiersoort.reducer';
import ecomponentsoort from 'app/entities/ecomponentsoort/ecomponentsoort.reducer';
import fraudegegevens from 'app/entities/fraudegegevens/fraudegegevens.reducer';
import fraudesoort from 'app/entities/fraudesoort/fraudesoort.reducer';
import informatiedakloosheid from 'app/entities/informatiedakloosheid/informatiedakloosheid.reducer';
import inkomensvoorziening from 'app/entities/inkomensvoorziening/inkomensvoorziening.reducer';
import inkomensvoorzieningsoort from 'app/entities/inkomensvoorzieningsoort/inkomensvoorzieningsoort.reducer';
import participatiedossier from 'app/entities/participatiedossier/participatiedossier.reducer';
import redenbeeindiging from 'app/entities/redenbeeindiging/redenbeeindiging.reducer';
import regeling from 'app/entities/regeling/regeling.reducer';
import regelingsoort from 'app/entities/regelingsoort/regelingsoort.reducer';
import taalniveau from 'app/entities/taalniveau/taalniveau.reducer';
import tegenprestatie from 'app/entities/tegenprestatie/tegenprestatie.reducer';
import tegenprestatiehoogte from 'app/entities/tegenprestatiehoogte/tegenprestatiehoogte.reducer';
import trajectactiviteit from 'app/entities/trajectactiviteit/trajectactiviteit.reducer';
import trajectactiviteitsoort from 'app/entities/trajectactiviteitsoort/trajectactiviteitsoort.reducer';
import trajectsoort from 'app/entities/trajectsoort/trajectsoort.reducer';
import uitkeringsrun from 'app/entities/uitkeringsrun/uitkeringsrun.reducer';
import container from 'app/entities/container/container.reducer';
import containertype from 'app/entities/containertype/containertype.reducer';
import fractie from 'app/entities/fractie/fractie.reducer';
import milieustraat from 'app/entities/milieustraat/milieustraat.reducer';
import ophaalmoment from 'app/entities/ophaalmoment/ophaalmoment.reducer';
import prijsafspraak from 'app/entities/prijsafspraak/prijsafspraak.reducer';
import prijsregel from 'app/entities/prijsregel/prijsregel.reducer';
import rit from 'app/entities/rit/rit.reducer';
import eroute from 'app/entities/eroute/eroute.reducer';
import storting from 'app/entities/storting/storting.reducer';
import vuilniswagen from 'app/entities/vuilniswagen/vuilniswagen.reducer';
import vulgraadmeting from 'app/entities/vulgraadmeting/vulgraadmeting.reducer';
import aansluitput from 'app/entities/aansluitput/aansluitput.reducer';
import afvalbak from 'app/entities/afvalbak/afvalbak.reducer';
import bank from 'app/entities/bank/bank.reducer';
import beheerobject from 'app/entities/beheerobject/beheerobject.reducer';
import bemalingsgebied from 'app/entities/bemalingsgebied/bemalingsgebied.reducer';
import bergingsbassin from 'app/entities/bergingsbassin/bergingsbassin.reducer';
import boom from 'app/entities/boom/boom.reducer';
import bord from 'app/entities/bord/bord.reducer';
import bouwwerk from 'app/entities/bouwwerk/bouwwerk.reducer';
import brug from 'app/entities/brug/brug.reducer';
import drainageput from 'app/entities/drainageput/drainageput.reducer';
import ecoduct from 'app/entities/ecoduct/ecoduct.reducer';
import fietsparkeervoorziening from 'app/entities/fietsparkeervoorziening/fietsparkeervoorziening.reducer';
import filterput from 'app/entities/filterput/filterput.reducer';
import flyover from 'app/entities/flyover/flyover.reducer';
import functioneelgebied from 'app/entities/functioneelgebied/functioneelgebied.reducer';
import geluidsscherm from 'app/entities/geluidsscherm/geluidsscherm.reducer';
import gemaal from 'app/entities/gemaal/gemaal.reducer';
import groenobject from 'app/entities/groenobject/groenobject.reducer';
import infiltratieput from 'app/entities/infiltratieput/infiltratieput.reducer';
import installatie from 'app/entities/installatie/installatie.reducer';
import kademuur from 'app/entities/kademuur/kademuur.reducer';
import keermuur from 'app/entities/keermuur/keermuur.reducer';
import klimplant from 'app/entities/klimplant/klimplant.reducer';
import kolk from 'app/entities/kolk/kolk.reducer';
import kunstwerk from 'app/entities/kunstwerk/kunstwerk.reducer';
import leidingelement from 'app/entities/leidingelement/leidingelement.reducer';
import mast from 'app/entities/mast/mast.reducer';
import meubilair from 'app/entities/meubilair/meubilair.reducer';
import overbruggingsobject from 'app/entities/overbruggingsobject/overbruggingsobject.reducer';
import overstortconstructie from 'app/entities/overstortconstructie/overstortconstructie.reducer';
import paal from 'app/entities/paal/paal.reducer';
import pomp from 'app/entities/pomp/pomp.reducer';
import putdeksel from 'app/entities/putdeksel/putdeksel.reducer';
import rioleringsgebied from 'app/entities/rioleringsgebied/rioleringsgebied.reducer';
import rioolput from 'app/entities/rioolput/rioolput.reducer';
import scheiding from 'app/entities/scheiding/scheiding.reducer';
import sensor from 'app/entities/sensor/sensor.reducer';
import solitaireplant from 'app/entities/solitaireplant/solitaireplant.reducer';
import speelterrein from 'app/entities/speelterrein/speelterrein.reducer';
import speeltoestel from 'app/entities/speeltoestel/speeltoestel.reducer';
import sportterrein from 'app/entities/sportterrein/sportterrein.reducer';
import stuwgebied from 'app/entities/stuwgebied/stuwgebied.reducer';
import terreindeel from 'app/entities/terreindeel/terreindeel.reducer';
import tunnelobject from 'app/entities/tunnelobject/tunnelobject.reducer';
import uitlaatconstructie from 'app/entities/uitlaatconstructie/uitlaatconstructie.reducer';
import vegetatieobject from 'app/entities/vegetatieobject/vegetatieobject.reducer';
import verhardingsobject from 'app/entities/verhardingsobject/verhardingsobject.reducer';
import verkeersdrempel from 'app/entities/verkeersdrempel/verkeersdrempel.reducer';
import verlichtingsobject from 'app/entities/verlichtingsobject/verlichtingsobject.reducer';
import viaduct from 'app/entities/viaduct/viaduct.reducer';
import waterinrichtingsobject from 'app/entities/waterinrichtingsobject/waterinrichtingsobject.reducer';
import waterobject from 'app/entities/waterobject/waterobject.reducer';
import weginrichtingsobject from 'app/entities/weginrichtingsobject/weginrichtingsobject.reducer';
import areaal from 'app/entities/areaal/areaal.reducer';
import crowmelding from 'app/entities/crowmelding/crowmelding.reducer';
import deelplanveld from 'app/entities/deelplanveld/deelplanveld.reducer';
import faseoplevering from 'app/entities/faseoplevering/faseoplevering.reducer';
import geoobject from 'app/entities/geoobject/geoobject.reducer';
import grondbeheerder from 'app/entities/grondbeheerder/grondbeheerder.reducer';
import kadastralemutatie from 'app/entities/kadastralemutatie/kadastralemutatie.reducer';
import kwaliteitscatalogusopenbareruimte from 'app/entities/kwaliteitscatalogusopenbareruimte/kwaliteitscatalogusopenbareruimte.reducer';
import kwaliteitskenmerken from 'app/entities/kwaliteitskenmerken/kwaliteitskenmerken.reducer';
import elogboek from 'app/entities/elogboek/elogboek.reducer';
import meldingongeval from 'app/entities/meldingongeval/meldingongeval.reducer';
import moormelding from 'app/entities/moormelding/moormelding.reducer';
import omgevingsvergunning from 'app/entities/omgevingsvergunning/omgevingsvergunning.reducer';
import onderhoud from 'app/entities/onderhoud/onderhoud.reducer';
import opbreking from 'app/entities/opbreking/opbreking.reducer';
import procesverbaalmoormelding from 'app/entities/procesverbaalmoormelding/procesverbaalmoormelding.reducer';
import schouwronde from 'app/entities/schouwronde/schouwronde.reducer';
import storing from 'app/entities/storing/storing.reducer';
import taak from 'app/entities/taak/taak.reducer';
import uitvoerdergraafwerkzaamheden from 'app/entities/uitvoerdergraafwerkzaamheden/uitvoerdergraafwerkzaamheden.reducer';
import verkeerslicht from 'app/entities/verkeerslicht/verkeerslicht.reducer';
import gebouw from 'app/entities/gebouw/gebouw.reducer';
import huurwoningen from 'app/entities/huurwoningen/huurwoningen.reducer';
import koopwoningen from 'app/entities/koopwoningen/koopwoningen.reducer';
import plan from 'app/entities/plan/plan.reducer';
import projectleider from 'app/entities/projectleider/projectleider.reducer';
import projectontwikkelaar from 'app/entities/projectontwikkelaar/projectontwikkelaar.reducer';
import studentenwoningen from 'app/entities/studentenwoningen/studentenwoningen.reducer';
import bevoegdgezag from 'app/entities/bevoegdgezag/bevoegdgezag.reducer';
import gemachtigde from 'app/entities/gemachtigde/gemachtigde.reducer';
import initiatiefnemer from 'app/entities/initiatiefnemer/initiatiefnemer.reducer';
import projectactiviteit from 'app/entities/projectactiviteit/projectactiviteit.reducer';
import projectlocatie from 'app/entities/projectlocatie/projectlocatie.reducer';
import specificatie from 'app/entities/specificatie/specificatie.reducer';
import uitvoerendeinstantie from 'app/entities/uitvoerendeinstantie/uitvoerendeinstantie.reducer';
import verzoek from 'app/entities/verzoek/verzoek.reducer';
import omgevingsdocument from 'app/entities/omgevingsdocument/omgevingsdocument.reducer';
import regeltekst from 'app/entities/regeltekst/regeltekst.reducer';
import beperkingsgebied from 'app/entities/beperkingsgebied/beperkingsgebied.reducer';
import gebiedsaanwijzing from 'app/entities/gebiedsaanwijzing/gebiedsaanwijzing.reducer';
import idealisatie from 'app/entities/idealisatie/idealisatie.reducer';
import instructieregel from 'app/entities/instructieregel/instructieregel.reducer';
import juridischeregel from 'app/entities/juridischeregel/juridischeregel.reducer';
import norm from 'app/entities/norm/norm.reducer';
import normwaarde from 'app/entities/normwaarde/normwaarde.reducer';
import omgevingsnorm from 'app/entities/omgevingsnorm/omgevingsnorm.reducer';
import omgevingswaarde from 'app/entities/omgevingswaarde/omgevingswaarde.reducer';
import omgevingswaarderegel from 'app/entities/omgevingswaarderegel/omgevingswaarderegel.reducer';
import regelvooriedereen from 'app/entities/regelvooriedereen/regelvooriedereen.reducer';
import thema from 'app/entities/thema/thema.reducer';
import conclusie from 'app/entities/conclusie/conclusie.reducer';
import indieningsvereisten from 'app/entities/indieningsvereisten/indieningsvereisten.reducer';
import maatregelen from 'app/entities/maatregelen/maatregelen.reducer';
import toepasbareregel from 'app/entities/toepasbareregel/toepasbareregel.reducer';
import toepasbareregelbestand from 'app/entities/toepasbareregelbestand/toepasbareregelbestand.reducer';
import uitvoeringsregel from 'app/entities/uitvoeringsregel/uitvoeringsregel.reducer';
import applicatie from 'app/entities/applicatie/applicatie.reducer';
import attribuutsoort from 'app/entities/attribuutsoort/attribuutsoort.reducer';
import cmdbitem from 'app/entities/cmdbitem/cmdbitem.reducer';
import database from 'app/entities/database/database.reducer';
import datatype from 'app/entities/datatype/datatype.reducer';
import dienst from 'app/entities/dienst/dienst.reducer';
import domeinoftaakveld from 'app/entities/domeinoftaakveld/domeinoftaakveld.reducer';
import externebron from 'app/entities/externebron/externebron.reducer';
import gebruikerrol from 'app/entities/gebruikerrol/gebruikerrol.reducer';
import gegeven from 'app/entities/gegeven/gegeven.reducer';
import generalisatie from 'app/entities/generalisatie/generalisatie.reducer';
import hardware from 'app/entities/hardware/hardware.reducer';
import inventaris from 'app/entities/inventaris/inventaris.reducer';
import koppeling from 'app/entities/koppeling/koppeling.reducer';
import licentie from 'app/entities/licentie/licentie.reducer';
import linkbaarcmdbitem from 'app/entities/linkbaarcmdbitem/linkbaarcmdbitem.reducer';
import elog from 'app/entities/elog/elog.reducer';
import nertwerkcomponent from 'app/entities/nertwerkcomponent/nertwerkcomponent.reducer';
import notitie from 'app/entities/notitie/notitie.reducer';
import eobjecttype from 'app/entities/eobjecttype/eobjecttype.reducer';
import onderwerp from 'app/entities/onderwerp/onderwerp.reducer';
import epackage from 'app/entities/epackage/epackage.reducer';
import prijzenboek from 'app/entities/prijzenboek/prijzenboek.reducer';
import server from 'app/entities/server/server.reducer';
import software from 'app/entities/software/software.reducer';
import telefoniegegevens from 'app/entities/telefoniegegevens/telefoniegegevens.reducer';
import toegangsmiddel from 'app/entities/toegangsmiddel/toegangsmiddel.reducer';
import versie from 'app/entities/versie/versie.reducer';
import vervoersmiddel from 'app/entities/vervoersmiddel/vervoersmiddel.reducer';
import wijzigingsverzoek from 'app/entities/wijzigingsverzoek/wijzigingsverzoek.reducer';
import betaalmoment from 'app/entities/betaalmoment/betaalmoment.reducer';
import rapportagemoment from 'app/entities/rapportagemoment/rapportagemoment.reducer';
import sector from 'app/entities/sector/sector.reducer';
import subsidie from 'app/entities/subsidie/subsidie.reducer';
import subsidieaanvraag from 'app/entities/subsidieaanvraag/subsidieaanvraag.reducer';
import subsidiebeschikking from 'app/entities/subsidiebeschikking/subsidiebeschikking.reducer';
import subsidiecomponent from 'app/entities/subsidiecomponent/subsidiecomponent.reducer';
import subsidieprogramma from 'app/entities/subsidieprogramma/subsidieprogramma.reducer';
import aanbestedingvastgoed from 'app/entities/aanbestedingvastgoed/aanbestedingvastgoed.reducer';
import adresaanduiding from 'app/entities/adresaanduiding/adresaanduiding.reducer';
import bouwdeel from 'app/entities/bouwdeel/bouwdeel.reducer';
import bouwdeelelement from 'app/entities/bouwdeelelement/bouwdeelelement.reducer';
import cultuuronbebouwd from 'app/entities/cultuuronbebouwd/cultuuronbebouwd.reducer';
import eigenaar from 'app/entities/eigenaar/eigenaar.reducer';
import gebruiksdoel from 'app/entities/gebruiksdoel/gebruiksdoel.reducer';
import huurder from 'app/entities/huurder/huurder.reducer';
import kpbetrokkenbij from 'app/entities/kpbetrokkenbij/kpbetrokkenbij.reducer';
import kponstaanuit from 'app/entities/kponstaanuit/kponstaanuit.reducer';
import locatieaanduidingwozobject from 'app/entities/locatieaanduidingwozobject/locatieaanduidingwozobject.reducer';
import locatieonroerendezaak from 'app/entities/locatieonroerendezaak/locatieonroerendezaak.reducer';
import mjop from 'app/entities/mjop/mjop.reducer';
import mjopitem from 'app/entities/mjopitem/mjopitem.reducer';
import nadaanvullingbrp from 'app/entities/nadaanvullingbrp/nadaanvullingbrp.reducer';
import eobjectrelatie from 'app/entities/eobjectrelatie/eobjectrelatie.reducer';
import offerte from 'app/entities/offerte/offerte.reducer';
import pachter from 'app/entities/pachter/pachter.reducer';
import prijzenboekitem from 'app/entities/prijzenboekitem/prijzenboekitem.reducer';
import vastgoedcontract from 'app/entities/vastgoedcontract/vastgoedcontract.reducer';
import vastgoedcontractregel from 'app/entities/vastgoedcontractregel/vastgoedcontractregel.reducer';
import vastgoedobject from 'app/entities/vastgoedobject/vastgoedobject.reducer';
import verhuurbaareenheid from 'app/entities/verhuurbaareenheid/verhuurbaareenheid.reducer';
import werkbon from 'app/entities/werkbon/werkbon.reducer';
import wozbelang from 'app/entities/wozbelang/wozbelang.reducer';
import zakelijkrecht from 'app/entities/zakelijkrecht/zakelijkrecht.reducer';
import bedrijf from 'app/entities/bedrijf/bedrijf.reducer';
import activa from 'app/entities/activa/activa.reducer';
import activasoort from 'app/entities/activasoort/activasoort.reducer';
import bankafschrift from 'app/entities/bankafschrift/bankafschrift.reducer';
import bankafschriftregel from 'app/entities/bankafschriftregel/bankafschriftregel.reducer';
import bankrekening from 'app/entities/bankrekening/bankrekening.reducer';
import batch from 'app/entities/batch/batch.reducer';
import batchregel from 'app/entities/batchregel/batchregel.reducer';
import begroting from 'app/entities/begroting/begroting.reducer';
import begrotingregel from 'app/entities/begrotingregel/begrotingregel.reducer';
import debiteur from 'app/entities/debiteur/debiteur.reducer';
import factuur from 'app/entities/factuur/factuur.reducer';
import factuurregel from 'app/entities/factuurregel/factuurregel.reducer';
import hoofdrekening from 'app/entities/hoofdrekening/hoofdrekening.reducer';
import hoofdstuk from 'app/entities/hoofdstuk/hoofdstuk.reducer';
import inkooporder from 'app/entities/inkooporder/inkooporder.reducer';
import kostenplaats from 'app/entities/kostenplaats/kostenplaats.reducer';
import opdrachtgever from 'app/entities/opdrachtgever/opdrachtgever.reducer';
import opdrachtnemer from 'app/entities/opdrachtnemer/opdrachtnemer.reducer';
import subrekening from 'app/entities/subrekening/subrekening.reducer';
import werkorder from 'app/entities/werkorder/werkorder.reducer';
import beoordeling from 'app/entities/beoordeling/beoordeling.reducer';
import declaratiesoort from 'app/entities/declaratiesoort/declaratiesoort.reducer';
import dienstverband from 'app/entities/dienstverband/dienstverband.reducer';
import disciplinairemaatregel from 'app/entities/disciplinairemaatregel/disciplinairemaatregel.reducer';
import formatieplaats from 'app/entities/formatieplaats/formatieplaats.reducer';
import functiehuis from 'app/entities/functiehuis/functiehuis.reducer';
import genotenopleiding from 'app/entities/genotenopleiding/genotenopleiding.reducer';
import geweldsincident from 'app/entities/geweldsincident/geweldsincident.reducer';
import individueelkeuzebudget from 'app/entities/individueelkeuzebudget/individueelkeuzebudget.reducer';
import inzet from 'app/entities/inzet/inzet.reducer';
import keuzebudgetbesteding from 'app/entities/keuzebudgetbesteding/keuzebudgetbesteding.reducer';
import keuzebudgetbestedingsoort from 'app/entities/keuzebudgetbestedingsoort/keuzebudgetbestedingsoort.reducer';
import normprofiel from 'app/entities/normprofiel/normprofiel.reducer';
import onderwijsinstituut from 'app/entities/onderwijsinstituut/onderwijsinstituut.reducer';
import organisatorischeeenheidhr from 'app/entities/organisatorischeeenheidhr/organisatorischeeenheidhr.reducer';
import sollicitant from 'app/entities/sollicitant/sollicitant.reducer';
import sollicitatie from 'app/entities/sollicitatie/sollicitatie.reducer';
import sollicitatiegesprek from 'app/entities/sollicitatiegesprek/sollicitatiegesprek.reducer';
import soortdisciplinairemaatregel from 'app/entities/soortdisciplinairemaatregel/soortdisciplinairemaatregel.reducer';
import uren from 'app/entities/uren/uren.reducer';
import vacature from 'app/entities/vacature/vacature.reducer';
import verlof from 'app/entities/verlof/verlof.reducer';
import verlofsoort from 'app/entities/verlofsoort/verlofsoort.reducer';
import verzuim from 'app/entities/verzuim/verzuim.reducer';
import verzuimsoort from 'app/entities/verzuimsoort/verzuimsoort.reducer';
import werknemer from 'app/entities/werknemer/werknemer.reducer';
import aanbesteding from 'app/entities/aanbesteding/aanbesteding.reducer';
import aanbestedinginhuur from 'app/entities/aanbestedinginhuur/aanbestedinginhuur.reducer';
import aankondiging from 'app/entities/aankondiging/aankondiging.reducer';
import aanvraaginkooporder from 'app/entities/aanvraaginkooporder/aanvraaginkooporder.reducer';
import cpvcode from 'app/entities/cpvcode/cpvcode.reducer';
import formulierinhuur from 'app/entities/formulierinhuur/formulierinhuur.reducer';
import formulierverlenginginhuur from 'app/entities/formulierverlenginginhuur/formulierverlenginginhuur.reducer';
import inkooppakket from 'app/entities/inkooppakket/inkooppakket.reducer';
import kandidaat from 'app/entities/kandidaat/kandidaat.reducer';
import offerteaanvraag from 'app/entities/offerteaanvraag/offerteaanvraag.reducer';
import selectietabelaanbesteding from 'app/entities/selectietabelaanbesteding/selectietabelaanbesteding.reducer';
import startformulieraanbesteden from 'app/entities/startformulieraanbesteden/startformulieraanbesteden.reducer';
import uitnodiging from 'app/entities/uitnodiging/uitnodiging.reducer';
import aanvraagdata from 'app/entities/aanvraagdata/aanvraagdata.reducer';
import afspraakstatus from 'app/entities/afspraakstatus/afspraakstatus.reducer';
import artikel from 'app/entities/artikel/artikel.reducer';
import balieafspraak from 'app/entities/balieafspraak/balieafspraak.reducer';
import formuliersoort from 'app/entities/formuliersoort/formuliersoort.reducer';
import formuliersoortveld from 'app/entities/formuliersoortveld/formuliersoortveld.reducer';
import klantbeoordeling from 'app/entities/klantbeoordeling/klantbeoordeling.reducer';
import klantbeoordelingreden from 'app/entities/klantbeoordelingreden/klantbeoordelingreden.reducer';
import productofdienst from 'app/entities/productofdienst/productofdienst.reducer';
import telefoononderwerp from 'app/entities/telefoononderwerp/telefoononderwerp.reducer';
import telefoonstatus from 'app/entities/telefoonstatus/telefoonstatus.reducer';
import telefoontje from 'app/entities/telefoontje/telefoontje.reducer';
import nummeraanduiding from 'app/entities/nummeraanduiding/nummeraanduiding.reducer';
import buurt from 'app/entities/buurt/buurt.reducer';
import gemeente from 'app/entities/gemeente/gemeente.reducer';
import ligplaats from 'app/entities/ligplaats/ligplaats.reducer';
import adresseerbaarobject from 'app/entities/adresseerbaarobject/adresseerbaarobject.reducer';
import pand from 'app/entities/pand/pand.reducer';
import verblijfsobject from 'app/entities/verblijfsobject/verblijfsobject.reducer';
import wijk from 'app/entities/wijk/wijk.reducer';
import woonplaats from 'app/entities/woonplaats/woonplaats.reducer';
import periode from 'app/entities/periode/periode.reducer';
import foto from 'app/entities/foto/foto.reducer';
import gebiedengroep from 'app/entities/gebiedengroep/gebiedengroep.reducer';
import lijn from 'app/entities/lijn/lijn.reducer';
import lijnengroep from 'app/entities/lijnengroep/lijnengroep.reducer';
import puntengroep from 'app/entities/puntengroep/puntengroep.reducer';
import videoopname from 'app/entities/videoopname/videoopname.reducer';
import bedrijfsproces from 'app/entities/bedrijfsproces/bedrijfsproces.reducer';
import bedrijfsprocestype from 'app/entities/bedrijfsprocestype/bedrijfsprocestype.reducer';
import besluittype from 'app/entities/besluittype/besluittype.reducer';
import betaling from 'app/entities/betaling/betaling.reducer';
import betrokkene from 'app/entities/betrokkene/betrokkene.reducer';
import deelproces from 'app/entities/deelproces/deelproces.reducer';
import deelprocestype from 'app/entities/deelprocestype/deelprocestype.reducer';
import documenttype from 'app/entities/documenttype/documenttype.reducer';
import enkelvoudigdocument from 'app/entities/enkelvoudigdocument/enkelvoudigdocument.reducer';
import identificatiekenmerk from 'app/entities/identificatiekenmerk/identificatiekenmerk.reducer';
import klantcontact from 'app/entities/klantcontact/klantcontact.reducer';
import medewerker from 'app/entities/medewerker/medewerker.reducer';
import organisatorischeeenheid from 'app/entities/organisatorischeeenheid/organisatorischeeenheid.reducer';
import samengestelddocument from 'app/entities/samengestelddocument/samengestelddocument.reducer';
import statustype from 'app/entities/statustype/statustype.reducer';
import vestigingvanzaakbehandelendeorganisatie from 'app/entities/vestigingvanzaakbehandelendeorganisatie/vestigingvanzaakbehandelendeorganisatie.reducer';
import zaakorigineel from 'app/entities/zaakorigineel/zaakorigineel.reducer';
import zaaktype from 'app/entities/zaaktype/zaaktype.reducer';
import afwijkendbuitenlandscorrespondentieadresrol from 'app/entities/afwijkendbuitenlandscorrespondentieadresrol/afwijkendbuitenlandscorrespondentieadresrol.reducer';
import afwijkendcorrespondentiepostadresrol from 'app/entities/afwijkendcorrespondentiepostadresrol/afwijkendcorrespondentiepostadresrol.reducer';
import anderzaakobjectzaak from 'app/entities/anderzaakobjectzaak/anderzaakobjectzaak.reducer';
import contactpersoonrol from 'app/entities/contactpersoonrol/contactpersoonrol.reducer';
import kenmerkenzaak from 'app/entities/kenmerkenzaak/kenmerkenzaak.reducer';
import opschortingzaak from 'app/entities/opschortingzaak/opschortingzaak.reducer';
import verlengingzaak from 'app/entities/verlengingzaak/verlengingzaak.reducer';
import brondocumenten from 'app/entities/brondocumenten/brondocumenten.reducer';
import formelehistorie from 'app/entities/formelehistorie/formelehistorie.reducer';
import inonderzoek from 'app/entities/inonderzoek/inonderzoek.reducer';
import materielehistorie from 'app/entities/materielehistorie/materielehistorie.reducer';
import strijdigheidofnietigheid from 'app/entities/strijdigheidofnietigheid/strijdigheidofnietigheid.reducer';
import aantekening from 'app/entities/aantekening/aantekening.reducer';
import adresbuitenland from 'app/entities/adresbuitenland/adresbuitenland.reducer';
import briefadres from 'app/entities/briefadres/briefadres.reducer';
import nationaliteit from 'app/entities/nationaliteit/nationaliteit.reducer';
import onbestemdadres from 'app/entities/onbestemdadres/onbestemdadres.reducer';
import appartementsrecht from 'app/entities/appartementsrecht/appartementsrecht.reducer';
import appartementsrechtsplitsing from 'app/entities/appartementsrechtsplitsing/appartementsrechtsplitsing.reducer';
import begroeidterreindeel from 'app/entities/begroeidterreindeel/begroeidterreindeel.reducer';
import benoemdobject from 'app/entities/benoemdobject/benoemdobject.reducer';
import benoemdterrein from 'app/entities/benoemdterrein/benoemdterrein.reducer';
import gebouwdobject from 'app/entities/gebouwdobject/gebouwdobject.reducer';
import gebouwinstallatie from 'app/entities/gebouwinstallatie/gebouwinstallatie.reducer';
import inrichtingselement from 'app/entities/inrichtingselement/inrichtingselement.reducer';
import kadastraalperceel from 'app/entities/kadastraalperceel/kadastraalperceel.reducer';
import kadastraleonroerendezaak from 'app/entities/kadastraleonroerendezaak/kadastraleonroerendezaak.reducer';
import ingeschrevenpersoon from 'app/entities/ingeschrevenpersoon/ingeschrevenpersoon.reducer';
import kadastraleonroerendezaakaantekening from 'app/entities/kadastraleonroerendezaakaantekening/kadastraleonroerendezaakaantekening.reducer';
import ingezetene from 'app/entities/ingezetene/ingezetene.reducer';
import kunstwerkdeel from 'app/entities/kunstwerkdeel/kunstwerkdeel.reducer';
import maatschappelijkeactiviteit from 'app/entities/maatschappelijkeactiviteit/maatschappelijkeactiviteit.reducer';
import adresseerbaarobjectaanduiding from 'app/entities/adresseerbaarobjectaanduiding/adresseerbaarobjectaanduiding.reducer';
import onbegroeidterreindeel from 'app/entities/onbegroeidterreindeel/onbegroeidterreindeel.reducer';
import ondersteunendwaterdeel from 'app/entities/ondersteunendwaterdeel/ondersteunendwaterdeel.reducer';
import ondersteunendwegdeel from 'app/entities/ondersteunendwegdeel/ondersteunendwegdeel.reducer';
import overbruggingsdeel from 'app/entities/overbruggingsdeel/overbruggingsdeel.reducer';
import overigbenoemdterrein from 'app/entities/overigbenoemdterrein/overigbenoemdterrein.reducer';
import natuurlijkpersoon from 'app/entities/natuurlijkpersoon/natuurlijkpersoon.reducer';
import overigbouwwerk from 'app/entities/overigbouwwerk/overigbouwwerk.reducer';
import overiggebouwdobject from 'app/entities/overiggebouwdobject/overiggebouwdobject.reducer';
import nietnatuurlijkpersoon from 'app/entities/nietnatuurlijkpersoon/nietnatuurlijkpersoon.reducer';
import overigeadresseerbaarobjectaanduiding from 'app/entities/overigeadresseerbaarobjectaanduiding/overigeadresseerbaarobjectaanduiding.reducer';
import overigescheiding from 'app/entities/overigescheiding/overigescheiding.reducer';
import reisdocument from 'app/entities/reisdocument/reisdocument.reducer';
import rechtspersoon from 'app/entities/rechtspersoon/rechtspersoon.reducer';
import tenaamstelling from 'app/entities/tenaamstelling/tenaamstelling.reducer';
import tunneldeel from 'app/entities/tunneldeel/tunneldeel.reducer';
import vestiging from 'app/entities/vestiging/vestiging.reducer';
import verblijfstitel from 'app/entities/verblijfstitel/verblijfstitel.reducer';
import wozdeelobject from 'app/entities/wozdeelobject/wozdeelobject.reducer';
import wozwaarde from 'app/entities/wozwaarde/wozwaarde.reducer';
import zekerheidsrecht from 'app/entities/zekerheidsrecht/zekerheidsrecht.reducer';
import locatieaanduidingadreswozobject from 'app/entities/locatieaanduidingadreswozobject/locatieaanduidingadreswozobject.reducer';
import correspondentieadresbuitenland from 'app/entities/correspondentieadresbuitenland/correspondentieadresbuitenland.reducer';
import geboorteingeschrevennatuurlijkpersoon from 'app/entities/geboorteingeschrevennatuurlijkpersoon/geboorteingeschrevennatuurlijkpersoon.reducer';
import geboorteingeschrevenpersoon from 'app/entities/geboorteingeschrevenpersoon/geboorteingeschrevenpersoon.reducer';
import handelsnamenmaatschappelijkeactiviteit from 'app/entities/handelsnamenmaatschappelijkeactiviteit/handelsnamenmaatschappelijkeactiviteit.reducer';
import handelsnamenvestiging from 'app/entities/handelsnamenvestiging/handelsnamenvestiging.reducer';
import koopsomkadastraleonroerendezaak from 'app/entities/koopsomkadastraleonroerendezaak/koopsomkadastraleonroerendezaak.reducer';
import locatiekadastraleonroerendezaak from 'app/entities/locatiekadastraleonroerendezaak/locatiekadastraleonroerendezaak.reducer';
import migratieingeschrevennatuurlijkpersoon from 'app/entities/migratieingeschrevennatuurlijkpersoon/migratieingeschrevennatuurlijkpersoon.reducer';
import naamaanschrijvingnatuurlijkpersoon from 'app/entities/naamaanschrijvingnatuurlijkpersoon/naamaanschrijvingnatuurlijkpersoon.reducer';
import naamgebruiknatuurlijkpersoon from 'app/entities/naamgebruiknatuurlijkpersoon/naamgebruiknatuurlijkpersoon.reducer';
import naamnatuurlijkpersoon from 'app/entities/naamnatuurlijkpersoon/naamnatuurlijkpersoon.reducer';
import nationaliteitingeschrevennatuurlijkpersoon from 'app/entities/nationaliteitingeschrevennatuurlijkpersoon/nationaliteitingeschrevennatuurlijkpersoon.reducer';
import nederlandsenationaliteitingeschrevenpersoon from 'app/entities/nederlandsenationaliteitingeschrevenpersoon/nederlandsenationaliteitingeschrevenpersoon.reducer';
import ontbindinghuwelijkgeregistreerdpartnerschap from 'app/entities/ontbindinghuwelijkgeregistreerdpartnerschap/ontbindinghuwelijkgeregistreerdpartnerschap.reducer';
import overlijdeningeschrevennatuurlijkpersoon from 'app/entities/overlijdeningeschrevennatuurlijkpersoon/overlijdeningeschrevennatuurlijkpersoon.reducer';
import overlijdeningeschrevenpersoon from 'app/entities/overlijdeningeschrevenpersoon/overlijdeningeschrevenpersoon.reducer';
import postadres from 'app/entities/postadres/postadres.reducer';
import rekeningnummer from 'app/entities/rekeningnummer/rekeningnummer.reducer';
import samengesteldenaamnatuurlijkpersoon from 'app/entities/samengesteldenaamnatuurlijkpersoon/samengesteldenaamnatuurlijkpersoon.reducer';
import sbiactiviteitvestiging from 'app/entities/sbiactiviteitvestiging/sbiactiviteitvestiging.reducer';
import sluitingofaangaanhuwelijkofgeregistreerdpartnerschap from 'app/entities/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.reducer';
import soortfunctioneelgebied from 'app/entities/soortfunctioneelgebied/soortfunctioneelgebied.reducer';
import soortkunstwerk from 'app/entities/soortkunstwerk/soortkunstwerk.reducer';
import soortoverigbouwwerk from 'app/entities/soortoverigbouwwerk/soortoverigbouwwerk.reducer';
import soortscheiding from 'app/entities/soortscheiding/soortscheiding.reducer';
import soortspoor from 'app/entities/soortspoor/soortspoor.reducer';
import splitsingstekeningreferentie from 'app/entities/splitsingstekeningreferentie/splitsingstekeningreferentie.reducer';
import verblijfadresingeschrevennatuurlijkpersoon from 'app/entities/verblijfadresingeschrevennatuurlijkpersoon/verblijfadresingeschrevennatuurlijkpersoon.reducer';
import verblijfadresingeschrevenpersoon from 'app/entities/verblijfadresingeschrevenpersoon/verblijfadresingeschrevenpersoon.reducer';
import verblijfbuitenland from 'app/entities/verblijfbuitenland/verblijfbuitenland.reducer';
import verblijfbuitenlandsubject from 'app/entities/verblijfbuitenlandsubject/verblijfbuitenlandsubject.reducer';
import verblijfsrechtingeschrevennatuurlijkpersoon from 'app/entities/verblijfsrechtingeschrevennatuurlijkpersoon/verblijfsrechtingeschrevennatuurlijkpersoon.reducer';
import verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon from 'app/entities/verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon/verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.reducer';
import aanduidingverblijfsrecht from 'app/entities/aanduidingverblijfsrecht/aanduidingverblijfsrecht.reducer';
import autoriteitafgiftenederlandsreisdocument from 'app/entities/autoriteitafgiftenederlandsreisdocument/autoriteitafgiftenederlandsreisdocument.reducer';
import aardaantekening from 'app/entities/aardaantekening/aardaantekening.reducer';
import aardzakelijkrecht from 'app/entities/aardzakelijkrecht/aardzakelijkrecht.reducer';
import aardfiliatie from 'app/entities/aardfiliatie/aardfiliatie.reducer';
import academischetitel from 'app/entities/academischetitel/academischetitel.reducer';
import akrkadastralegemeentecode from 'app/entities/akrkadastralegemeentecode/akrkadastralegemeentecode.reducer';
import cultuurcodebebouwd from 'app/entities/cultuurcodebebouwd/cultuurcodebebouwd.reducer';
import cultuurcodeonbebouwd from 'app/entities/cultuurcodeonbebouwd/cultuurcodeonbebouwd.reducer';
import wozdeelobjectcode from 'app/entities/wozdeelobjectcode/wozdeelobjectcode.reducer';
import kadastralegemeente from 'app/entities/kadastralegemeente/kadastralegemeente.reducer';
import landofgebied from 'app/entities/landofgebied/landofgebied.reducer';
import provincie from 'app/entities/provincie/provincie.reducer';
import partij from 'app/entities/partij/partij.reducer';
import redenverkrijgingnationaliteit from 'app/entities/redenverkrijgingnationaliteit/redenverkrijgingnationaliteit.reducer';
import redenverliesnationaliteit from 'app/entities/redenverliesnationaliteit/redenverliesnationaliteit.reducer';
import reisdocumentsoort from 'app/entities/reisdocumentsoort/reisdocumentsoort.reducer';
import sbiactiviteit from 'app/entities/sbiactiviteit/sbiactiviteit.reducer';
import soortgrootte from 'app/entities/soortgrootte/soortgrootte.reducer';
import soortwozobject from 'app/entities/soortwozobject/soortwozobject.reducer';
import valutasoort from 'app/entities/valutasoort/valutasoort.reducer';
import valuta from 'app/entities/valuta/valuta.reducer';
import eobjecttypea from 'app/entities/eobjecttypea/eobjecttypea.reducer';
import eobjecttypeb from 'app/entities/eobjecttypeb/eobjecttypeb.reducer';
import eobjecttypec from 'app/entities/eobjecttypec/eobjecttypec.reducer';
import eobjecttyped from 'app/entities/eobjecttyped/eobjecttyped.reducer';
import eobjecttypee from 'app/entities/eobjecttypee/eobjecttypee.reducer';
import eobjecttypef from 'app/entities/eobjecttypef/eobjecttypef.reducer';
import eobjecttypeg from 'app/entities/eobjecttypeg/eobjecttypeg.reducer';
import childclassa from 'app/entities/childclassa/childclassa.reducer';
import classb from 'app/entities/classb/classb.reducer';
import classc from 'app/entities/classc/classc.reducer';
import class1 from 'app/entities/class-1/class-1.reducer';
import classd from 'app/entities/classd/classd.reducer';
import classe from 'app/entities/classe/classe.reducer';
import classf from 'app/entities/classf/classf.reducer';
import classg from 'app/entities/classg/classg.reducer';
import classh from 'app/entities/classh/classh.reducer';
import classi from 'app/entities/classi/classi.reducer';
import classj from 'app/entities/classj/classj.reducer';
import kpclassaclassc from 'app/entities/kpclassaclassc/kpclassaclassc.reducer';
import enumenumerationa from 'app/entities/enumenumerationa/enumenumerationa.reducer';
import lstclass1 from 'app/entities/lstclass-1/lstclass-1.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  aanwezigedeelnemer,
  agendapunt,
  categorie,
  collegelid,
  dossier,
  indiener,
  programma,
  raadscommissie,
  raadslid,
  raadsstuk,
  stemming,
  taakveld,
  vergadering,
  activiteit,
  aomstatus,
  bevinding,
  boa,
  combibon,
  fietsregistratie,
  grondslag,
  heffinggrondslag,
  heffingsverordening,
  inspectie,
  kosten,
  legesgrondslag,
  ligplaatsontheffing,
  moraanvraagofmelding,
  openbareactiviteit,
  precario,
  producttype,
  subproducttype,
  vaartuig,
  vomaanvraagofmelding,
  vordering,
  vorderingregel,
  vthaanvraagofmelding,
  vthmelding,
  vthzaak,
  waarneming,
  waboaanvraagofmelding,
  woonfraudeaanvraagofmelding,
  woonoverlastaanvraagofmelding,
  belprovider,
  mulderfeit,
  naheffing,
  parkeergarage,
  parkeerrecht,
  parkeerscan,
  parkeervergunning,
  parkeervlak,
  parkeerzone,
  productgroep,
  productsoort,
  straatsectie,
  voertuig,
  stremming,
  strooidag,
  strooiroute,
  strooirouteuitvoering,
  verkeersbesluit,
  verkeerstelling,
  vloginfo,
  contact,
  hotel,
  hotelbezoek,
  verkooppunt,
  werkgelegenheid,
  winkelvloeroppervlak,
  aanvraagleerlingenvervoer,
  aanvraagvrijstelling,
  beschikkingleerlingenvervoer,
  beslissing,
  doorgeleidingom,
  haltverwijzing,
  klachtleerlingenvervoer,
  leerplichtambtenaar,
  procesverbaalonderwijs,
  verlofaanvraag,
  vervoerder,
  verzuimmelding,
  ziekmeldingleerlingenvervoer,
  inschrijving,
  leerjaar,
  leerling,
  locatie,
  loopbaanstap,
  onderwijsloopbaan,
  onderwijsniveau,
  onderwijssoort,
  ouderofverzorger,
  school,
  startkwalificatie,
  uitschrijving,
  archeologiebesluit,
  artefact,
  artefactsoort,
  boring,
  doos,
  kaart,
  magazijnlocatie,
  magazijnplaatsing,
  project,
  put,
  spoor,
  vindplaats,
  vondst,
  vulling,
  archief,
  archiefcategorie,
  archiefstuk,
  auteur,
  bezoeker,
  depot,
  digitaalbestand,
  indeling,
  index,
  kast,
  naderetoegang,
  ordeningsschema,
  plank,
  rechthebbende,
  uitgever,
  erfgoedobject,
  historischerol,
  historischpersoon,
  eobjectclassificatie,
  ambacht,
  beschermdestatus,
  bouwactiviteit,
  bouwstijl,
  bouwtype,
  oorspronkelijkefunctie,
  activiteitsoort,
  balieverkoop,
  balieverkoopentreekaart,
  belanghebbende,
  belangtype,
  bruikleen,
  collectie,
  doelgroep,
  incident,
  lener,
  mailing,
  museumobject,
  museumrelatie,
  omzetgroep,
  prijs,
  product,
  productieeenheid,
  programmasoort,
  reservering,
  rondleiding,
  samensteller,
  standplaats,
  tentoonstelling,
  voorziening,
  winkelverkoopgroep,
  winkelvoorraaditem,
  zaal,
  aantal,
  belijning,
  bezetting,
  binnenlocatie,
  onderhoudskosten,
  sport,
  sportlocatie,
  sportmateriaal,
  sportpark,
  sportvereniging,
  gemeentebegrafenis,
  aanvraagstadspas,
  aomaanvraagwmojeugd,
  aommeldingwmojeugd,
  beperking,
  beperkingscategorie,
  beperkingscore,
  beperkingscoresoort,
  beschikking,
  beschikkingsoort,
  beschiktevoorziening,
  budgetuitputting,
  client,
  clientbegeleider,
  declaratie,
  declaratieregel,
  huishouden,
  leefgebied,
  leverancier,
  levering,
  leveringsvorm,
  meldingeigenbijdrage,
  pgbtoekenning,
  relatiesoort,
  tarief,
  team,
  toewijzing,
  verplichtingwmojeugd,
  verzoekomtoewijzing,
  voorzieningsoort,
  zelfredzaamheidmatrix,
  asielstatushouder,
  b1,
  bredeintake,
  examen,
  examenonderdeel,
  gezinsmigrantenoverigemigrant,
  inburgeraar,
  inburgeringstraject,
  knm,
  leerroute,
  map,
  participatiecomponent,
  pip,
  pvt,
  verblijfplaats,
  verblijfplaatsazc,
  vreemdeling,
  z,
  financielesituatie,
  notarielestatus,
  projectsoort,
  resultaat,
  resultaatsoort,
  uitstroomreden,
  uitstroomredensoort,
  behandeling,
  behandelsoort,
  bijzonderheid,
  bijzonderheidsoort,
  caseaanmelding,
  doelstelling,
  doelstellingsoort,
  sociaalteamdossier,
  sociaalteamdossiersoort,
  ecomponentsoort,
  fraudegegevens,
  fraudesoort,
  informatiedakloosheid,
  inkomensvoorziening,
  inkomensvoorzieningsoort,
  participatiedossier,
  redenbeeindiging,
  regeling,
  regelingsoort,
  taalniveau,
  tegenprestatie,
  tegenprestatiehoogte,
  trajectactiviteit,
  trajectactiviteitsoort,
  trajectsoort,
  uitkeringsrun,
  container,
  containertype,
  fractie,
  milieustraat,
  ophaalmoment,
  prijsafspraak,
  prijsregel,
  rit,
  eroute,
  storting,
  vuilniswagen,
  vulgraadmeting,
  aansluitput,
  afvalbak,
  bank,
  beheerobject,
  bemalingsgebied,
  bergingsbassin,
  boom,
  bord,
  bouwwerk,
  brug,
  drainageput,
  ecoduct,
  fietsparkeervoorziening,
  filterput,
  flyover,
  functioneelgebied,
  geluidsscherm,
  gemaal,
  groenobject,
  infiltratieput,
  installatie,
  kademuur,
  keermuur,
  klimplant,
  kolk,
  kunstwerk,
  leidingelement,
  mast,
  meubilair,
  overbruggingsobject,
  overstortconstructie,
  paal,
  pomp,
  putdeksel,
  rioleringsgebied,
  rioolput,
  scheiding,
  sensor,
  solitaireplant,
  speelterrein,
  speeltoestel,
  sportterrein,
  stuwgebied,
  terreindeel,
  tunnelobject,
  uitlaatconstructie,
  vegetatieobject,
  verhardingsobject,
  verkeersdrempel,
  verlichtingsobject,
  viaduct,
  waterinrichtingsobject,
  waterobject,
  weginrichtingsobject,
  areaal,
  crowmelding,
  deelplanveld,
  faseoplevering,
  geoobject,
  grondbeheerder,
  kadastralemutatie,
  kwaliteitscatalogusopenbareruimte,
  kwaliteitskenmerken,
  elogboek,
  meldingongeval,
  moormelding,
  omgevingsvergunning,
  onderhoud,
  opbreking,
  procesverbaalmoormelding,
  schouwronde,
  storing,
  taak,
  uitvoerdergraafwerkzaamheden,
  verkeerslicht,
  gebouw,
  huurwoningen,
  koopwoningen,
  plan,
  projectleider,
  projectontwikkelaar,
  studentenwoningen,
  bevoegdgezag,
  gemachtigde,
  initiatiefnemer,
  projectactiviteit,
  projectlocatie,
  specificatie,
  uitvoerendeinstantie,
  verzoek,
  omgevingsdocument,
  regeltekst,
  beperkingsgebied,
  gebiedsaanwijzing,
  idealisatie,
  instructieregel,
  juridischeregel,
  norm,
  normwaarde,
  omgevingsnorm,
  omgevingswaarde,
  omgevingswaarderegel,
  regelvooriedereen,
  thema,
  conclusie,
  indieningsvereisten,
  maatregelen,
  toepasbareregel,
  toepasbareregelbestand,
  uitvoeringsregel,
  applicatie,
  attribuutsoort,
  cmdbitem,
  database,
  datatype,
  dienst,
  domeinoftaakveld,
  externebron,
  gebruikerrol,
  gegeven,
  generalisatie,
  hardware,
  inventaris,
  koppeling,
  licentie,
  linkbaarcmdbitem,
  elog,
  nertwerkcomponent,
  notitie,
  eobjecttype,
  onderwerp,
  epackage,
  prijzenboek,
  server,
  software,
  telefoniegegevens,
  toegangsmiddel,
  versie,
  vervoersmiddel,
  wijzigingsverzoek,
  betaalmoment,
  rapportagemoment,
  sector,
  subsidie,
  subsidieaanvraag,
  subsidiebeschikking,
  subsidiecomponent,
  subsidieprogramma,
  aanbestedingvastgoed,
  adresaanduiding,
  bouwdeel,
  bouwdeelelement,
  cultuuronbebouwd,
  eigenaar,
  gebruiksdoel,
  huurder,
  kpbetrokkenbij,
  kponstaanuit,
  locatieaanduidingwozobject,
  locatieonroerendezaak,
  mjop,
  mjopitem,
  nadaanvullingbrp,
  eobjectrelatie,
  offerte,
  pachter,
  prijzenboekitem,
  vastgoedcontract,
  vastgoedcontractregel,
  vastgoedobject,
  verhuurbaareenheid,
  werkbon,
  wozbelang,
  zakelijkrecht,
  bedrijf,
  activa,
  activasoort,
  bankafschrift,
  bankafschriftregel,
  bankrekening,
  batch,
  batchregel,
  begroting,
  begrotingregel,
  debiteur,
  factuur,
  factuurregel,
  hoofdrekening,
  hoofdstuk,
  inkooporder,
  kostenplaats,
  opdrachtgever,
  opdrachtnemer,
  subrekening,
  werkorder,
  beoordeling,
  declaratiesoort,
  dienstverband,
  disciplinairemaatregel,
  formatieplaats,
  functiehuis,
  genotenopleiding,
  geweldsincident,
  individueelkeuzebudget,
  inzet,
  keuzebudgetbesteding,
  keuzebudgetbestedingsoort,
  normprofiel,
  onderwijsinstituut,
  organisatorischeeenheidhr,
  sollicitant,
  sollicitatie,
  sollicitatiegesprek,
  soortdisciplinairemaatregel,
  uren,
  vacature,
  verlof,
  verlofsoort,
  verzuim,
  verzuimsoort,
  werknemer,
  aanbesteding,
  aanbestedinginhuur,
  aankondiging,
  aanvraaginkooporder,
  cpvcode,
  formulierinhuur,
  formulierverlenginginhuur,
  inkooppakket,
  kandidaat,
  offerteaanvraag,
  selectietabelaanbesteding,
  startformulieraanbesteden,
  uitnodiging,
  aanvraagdata,
  afspraakstatus,
  artikel,
  balieafspraak,
  formuliersoort,
  formuliersoortveld,
  klantbeoordeling,
  klantbeoordelingreden,
  productofdienst,
  telefoononderwerp,
  telefoonstatus,
  telefoontje,
  nummeraanduiding,
  buurt,
  gemeente,
  ligplaats,
  adresseerbaarobject,
  pand,
  verblijfsobject,
  wijk,
  woonplaats,
  periode,
  foto,
  gebiedengroep,
  lijn,
  lijnengroep,
  puntengroep,
  videoopname,
  bedrijfsproces,
  bedrijfsprocestype,
  besluittype,
  betaling,
  betrokkene,
  deelproces,
  deelprocestype,
  documenttype,
  enkelvoudigdocument,
  identificatiekenmerk,
  klantcontact,
  medewerker,
  organisatorischeeenheid,
  samengestelddocument,
  statustype,
  vestigingvanzaakbehandelendeorganisatie,
  zaakorigineel,
  zaaktype,
  afwijkendbuitenlandscorrespondentieadresrol,
  afwijkendcorrespondentiepostadresrol,
  anderzaakobjectzaak,
  contactpersoonrol,
  kenmerkenzaak,
  opschortingzaak,
  verlengingzaak,
  brondocumenten,
  formelehistorie,
  inonderzoek,
  materielehistorie,
  strijdigheidofnietigheid,
  aantekening,
  adresbuitenland,
  briefadres,
  nationaliteit,
  onbestemdadres,
  appartementsrecht,
  appartementsrechtsplitsing,
  begroeidterreindeel,
  benoemdobject,
  benoemdterrein,
  gebouwdobject,
  gebouwinstallatie,
  inrichtingselement,
  kadastraalperceel,
  kadastraleonroerendezaak,
  ingeschrevenpersoon,
  kadastraleonroerendezaakaantekening,
  ingezetene,
  kunstwerkdeel,
  maatschappelijkeactiviteit,
  adresseerbaarobjectaanduiding,
  onbegroeidterreindeel,
  ondersteunendwaterdeel,
  ondersteunendwegdeel,
  overbruggingsdeel,
  overigbenoemdterrein,
  natuurlijkpersoon,
  overigbouwwerk,
  overiggebouwdobject,
  nietnatuurlijkpersoon,
  overigeadresseerbaarobjectaanduiding,
  overigescheiding,
  reisdocument,
  rechtspersoon,
  tenaamstelling,
  tunneldeel,
  vestiging,
  verblijfstitel,
  wozdeelobject,
  wozwaarde,
  zekerheidsrecht,
  locatieaanduidingadreswozobject,
  correspondentieadresbuitenland,
  geboorteingeschrevennatuurlijkpersoon,
  geboorteingeschrevenpersoon,
  handelsnamenmaatschappelijkeactiviteit,
  handelsnamenvestiging,
  koopsomkadastraleonroerendezaak,
  locatiekadastraleonroerendezaak,
  migratieingeschrevennatuurlijkpersoon,
  naamaanschrijvingnatuurlijkpersoon,
  naamgebruiknatuurlijkpersoon,
  naamnatuurlijkpersoon,
  nationaliteitingeschrevennatuurlijkpersoon,
  nederlandsenationaliteitingeschrevenpersoon,
  ontbindinghuwelijkgeregistreerdpartnerschap,
  overlijdeningeschrevennatuurlijkpersoon,
  overlijdeningeschrevenpersoon,
  postadres,
  rekeningnummer,
  samengesteldenaamnatuurlijkpersoon,
  sbiactiviteitvestiging,
  sluitingofaangaanhuwelijkofgeregistreerdpartnerschap,
  soortfunctioneelgebied,
  soortkunstwerk,
  soortoverigbouwwerk,
  soortscheiding,
  soortspoor,
  splitsingstekeningreferentie,
  verblijfadresingeschrevennatuurlijkpersoon,
  verblijfadresingeschrevenpersoon,
  verblijfbuitenland,
  verblijfbuitenlandsubject,
  verblijfsrechtingeschrevennatuurlijkpersoon,
  verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon,
  aanduidingverblijfsrecht,
  autoriteitafgiftenederlandsreisdocument,
  aardaantekening,
  aardzakelijkrecht,
  aardfiliatie,
  academischetitel,
  akrkadastralegemeentecode,
  cultuurcodebebouwd,
  cultuurcodeonbebouwd,
  wozdeelobjectcode,
  kadastralegemeente,
  landofgebied,
  provincie,
  partij,
  redenverkrijgingnationaliteit,
  redenverliesnationaliteit,
  reisdocumentsoort,
  sbiactiviteit,
  soortgrootte,
  soortwozobject,
  valutasoort,
  valuta,
  eobjecttypea,
  eobjecttypeb,
  eobjecttypec,
  eobjecttyped,
  eobjecttypee,
  eobjecttypef,
  eobjecttypeg,
  childclassa,
  classb,
  classc,
  class1,
  classd,
  classe,
  classf,
  classg,
  classh,
  classi,
  classj,
  kpclassaclassc,
  enumenumerationa,
  lstclass1,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
