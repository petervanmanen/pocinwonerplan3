import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './vastgoedobject.reducer';

export const Vastgoedobject = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vastgoedobjectList = useAppSelector(state => state.vastgoedobject.entities);
  const loading = useAppSelector(state => state.vastgoedobject.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="vastgoedobject-heading" data-cy="VastgoedobjectHeading">
        Vastgoedobjects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vastgoedobject/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vastgoedobject
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vastgoedobjectList && vastgoedobjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantaletages')}>
                  Aantaletages <FontAwesomeIcon icon={getSortIconByFieldName('aantaletages')} />
                </th>
                <th className="hand" onClick={sort('aantalparkeerplaatsen')}>
                  Aantalparkeerplaatsen <FontAwesomeIcon icon={getSortIconByFieldName('aantalparkeerplaatsen')} />
                </th>
                <th className="hand" onClick={sort('aantalrioleringen')}>
                  Aantalrioleringen <FontAwesomeIcon icon={getSortIconByFieldName('aantalrioleringen')} />
                </th>
                <th className="hand" onClick={sort('adresaanduiding')}>
                  Adresaanduiding <FontAwesomeIcon icon={getSortIconByFieldName('adresaanduiding')} />
                </th>
                <th className="hand" onClick={sort('afgekochteerfpacht')}>
                  Afgekochteerfpacht <FontAwesomeIcon icon={getSortIconByFieldName('afgekochteerfpacht')} />
                </th>
                <th className="hand" onClick={sort('afgesprokenconditiescore')}>
                  Afgesprokenconditiescore <FontAwesomeIcon icon={getSortIconByFieldName('afgesprokenconditiescore')} />
                </th>
                <th className="hand" onClick={sort('afkoopwaarde')}>
                  Afkoopwaarde <FontAwesomeIcon icon={getSortIconByFieldName('afkoopwaarde')} />
                </th>
                <th className="hand" onClick={sort('asbestrapportageaanwezig')}>
                  Asbestrapportageaanwezig <FontAwesomeIcon icon={getSortIconByFieldName('asbestrapportageaanwezig')} />
                </th>
                <th className="hand" onClick={sort('bedragaankoop')}>
                  Bedragaankoop <FontAwesomeIcon icon={getSortIconByFieldName('bedragaankoop')} />
                </th>
                <th className="hand" onClick={sort('bestemmingsplan')}>
                  Bestemmingsplan <FontAwesomeIcon icon={getSortIconByFieldName('bestemmingsplan')} />
                </th>
                <th className="hand" onClick={sort('boekwaarde')}>
                  Boekwaarde <FontAwesomeIcon icon={getSortIconByFieldName('boekwaarde')} />
                </th>
                <th className="hand" onClick={sort('bouwjaar')}>
                  Bouwjaar <FontAwesomeIcon icon={getSortIconByFieldName('bouwjaar')} />
                </th>
                <th className="hand" onClick={sort('bouwwerk')}>
                  Bouwwerk <FontAwesomeIcon icon={getSortIconByFieldName('bouwwerk')} />
                </th>
                <th className="hand" onClick={sort('bovenliggendniveau')}>
                  Bovenliggendniveau <FontAwesomeIcon icon={getSortIconByFieldName('bovenliggendniveau')} />
                </th>
                <th className="hand" onClick={sort('bovenliggendniveaucode')}>
                  Bovenliggendniveaucode <FontAwesomeIcon icon={getSortIconByFieldName('bovenliggendniveaucode')} />
                </th>
                <th className="hand" onClick={sort('brutovloeroppervlakte')}>
                  Brutovloeroppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('brutovloeroppervlakte')} />
                </th>
                <th className="hand" onClick={sort('co2uitstoot')}>
                  Co 2 Uitstoot <FontAwesomeIcon icon={getSortIconByFieldName('co2uitstoot')} />
                </th>
                <th className="hand" onClick={sort('conditiescore')}>
                  Conditiescore <FontAwesomeIcon icon={getSortIconByFieldName('conditiescore')} />
                </th>
                <th className="hand" onClick={sort('datumafstoten')}>
                  Datumafstoten <FontAwesomeIcon icon={getSortIconByFieldName('datumafstoten')} />
                </th>
                <th className="hand" onClick={sort('datumberekeningoppervlak')}>
                  Datumberekeningoppervlak <FontAwesomeIcon icon={getSortIconByFieldName('datumberekeningoppervlak')} />
                </th>
                <th className="hand" onClick={sort('datumeigendom')}>
                  Datumeigendom <FontAwesomeIcon icon={getSortIconByFieldName('datumeigendom')} />
                </th>
                <th className="hand" onClick={sort('datumverkoop')}>
                  Datumverkoop <FontAwesomeIcon icon={getSortIconByFieldName('datumverkoop')} />
                </th>
                <th className="hand" onClick={sort('deelportefeuille')}>
                  Deelportefeuille <FontAwesomeIcon icon={getSortIconByFieldName('deelportefeuille')} />
                </th>
                <th className="hand" onClick={sort('energiekosten')}>
                  Energiekosten <FontAwesomeIcon icon={getSortIconByFieldName('energiekosten')} />
                </th>
                <th className="hand" onClick={sort('energielabel')}>
                  Energielabel <FontAwesomeIcon icon={getSortIconByFieldName('energielabel')} />
                </th>
                <th className="hand" onClick={sort('energieverbruik')}>
                  Energieverbruik <FontAwesomeIcon icon={getSortIconByFieldName('energieverbruik')} />
                </th>
                <th className="hand" onClick={sort('fiscalewaarde')}>
                  Fiscalewaarde <FontAwesomeIcon icon={getSortIconByFieldName('fiscalewaarde')} />
                </th>
                <th className="hand" onClick={sort('foto')}>
                  Foto <FontAwesomeIcon icon={getSortIconByFieldName('foto')} />
                </th>
                <th className="hand" onClick={sort('gearchiveerd')}>
                  Gearchiveerd <FontAwesomeIcon icon={getSortIconByFieldName('gearchiveerd')} />
                </th>
                <th className="hand" onClick={sort('herbouwwaarde')}>
                  Herbouwwaarde <FontAwesomeIcon icon={getSortIconByFieldName('herbouwwaarde')} />
                </th>
                <th className="hand" onClick={sort('hoofdstuk')}>
                  Hoofdstuk <FontAwesomeIcon icon={getSortIconByFieldName('hoofdstuk')} />
                </th>
                <th className="hand" onClick={sort('identificatie')}>
                  Identificatie <FontAwesomeIcon icon={getSortIconByFieldName('identificatie')} />
                </th>
                <th className="hand" onClick={sort('jaarlaatsterenovatie')}>
                  Jaarlaatsterenovatie <FontAwesomeIcon icon={getSortIconByFieldName('jaarlaatsterenovatie')} />
                </th>
                <th className="hand" onClick={sort('locatie')}>
                  Locatie <FontAwesomeIcon icon={getSortIconByFieldName('locatie')} />
                </th>
                <th className="hand" onClick={sort('marktwaarde')}>
                  Marktwaarde <FontAwesomeIcon icon={getSortIconByFieldName('marktwaarde')} />
                </th>
                <th className="hand" onClick={sort('monument')}>
                  Monument <FontAwesomeIcon icon={getSortIconByFieldName('monument')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('eobjectstatus')}>
                  Eobjectstatus <FontAwesomeIcon icon={getSortIconByFieldName('eobjectstatus')} />
                </th>
                <th className="hand" onClick={sort('eobjectstatuscode')}>
                  Eobjectstatuscode <FontAwesomeIcon icon={getSortIconByFieldName('eobjectstatuscode')} />
                </th>
                <th className="hand" onClick={sort('eobjecttype')}>
                  Eobjecttype <FontAwesomeIcon icon={getSortIconByFieldName('eobjecttype')} />
                </th>
                <th className="hand" onClick={sort('eobjecttypecode')}>
                  Eobjecttypecode <FontAwesomeIcon icon={getSortIconByFieldName('eobjecttypecode')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('onderhoudscategorie')}>
                  Onderhoudscategorie <FontAwesomeIcon icon={getSortIconByFieldName('onderhoudscategorie')} />
                </th>
                <th className="hand" onClick={sort('oppervlaktekantoor')}>
                  Oppervlaktekantoor <FontAwesomeIcon icon={getSortIconByFieldName('oppervlaktekantoor')} />
                </th>
                <th className="hand" onClick={sort('portefeuille')}>
                  Portefeuille <FontAwesomeIcon icon={getSortIconByFieldName('portefeuille')} />
                </th>
                <th className="hand" onClick={sort('portefeuillecode')}>
                  Portefeuillecode <FontAwesomeIcon icon={getSortIconByFieldName('portefeuillecode')} />
                </th>
                <th className="hand" onClick={sort('provincie')}>
                  Provincie <FontAwesomeIcon icon={getSortIconByFieldName('provincie')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th className="hand" onClick={sort('verhuurbaarvloeroppervlak')}>
                  Verhuurbaarvloeroppervlak <FontAwesomeIcon icon={getSortIconByFieldName('verhuurbaarvloeroppervlak')} />
                </th>
                <th className="hand" onClick={sort('verkoopbaarheid')}>
                  Verkoopbaarheid <FontAwesomeIcon icon={getSortIconByFieldName('verkoopbaarheid')} />
                </th>
                <th className="hand" onClick={sort('verkoopbedrag')}>
                  Verkoopbedrag <FontAwesomeIcon icon={getSortIconByFieldName('verkoopbedrag')} />
                </th>
                <th className="hand" onClick={sort('verzekerdewaarde')}>
                  Verzekerdewaarde <FontAwesomeIcon icon={getSortIconByFieldName('verzekerdewaarde')} />
                </th>
                <th className="hand" onClick={sort('waardegrond')}>
                  Waardegrond <FontAwesomeIcon icon={getSortIconByFieldName('waardegrond')} />
                </th>
                <th className="hand" onClick={sort('waardeopstal')}>
                  Waardeopstal <FontAwesomeIcon icon={getSortIconByFieldName('waardeopstal')} />
                </th>
                <th className="hand" onClick={sort('wijk')}>
                  Wijk <FontAwesomeIcon icon={getSortIconByFieldName('wijk')} />
                </th>
                <th className="hand" onClick={sort('wozwaarde')}>
                  Wozwaarde <FontAwesomeIcon icon={getSortIconByFieldName('wozwaarde')} />
                </th>
                <th>
                  Betreft Pand <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Kostenplaats <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vastgoedobjectList.map((vastgoedobject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vastgoedobject/${vastgoedobject.id}`} color="link" size="sm">
                      {vastgoedobject.id}
                    </Button>
                  </td>
                  <td>{vastgoedobject.aantaletages}</td>
                  <td>{vastgoedobject.aantalparkeerplaatsen}</td>
                  <td>{vastgoedobject.aantalrioleringen}</td>
                  <td>{vastgoedobject.adresaanduiding}</td>
                  <td>{vastgoedobject.afgekochteerfpacht}</td>
                  <td>{vastgoedobject.afgesprokenconditiescore}</td>
                  <td>{vastgoedobject.afkoopwaarde}</td>
                  <td>{vastgoedobject.asbestrapportageaanwezig}</td>
                  <td>{vastgoedobject.bedragaankoop}</td>
                  <td>{vastgoedobject.bestemmingsplan}</td>
                  <td>{vastgoedobject.boekwaarde}</td>
                  <td>{vastgoedobject.bouwjaar}</td>
                  <td>{vastgoedobject.bouwwerk}</td>
                  <td>{vastgoedobject.bovenliggendniveau}</td>
                  <td>{vastgoedobject.bovenliggendniveaucode}</td>
                  <td>{vastgoedobject.brutovloeroppervlakte}</td>
                  <td>{vastgoedobject.co2uitstoot}</td>
                  <td>{vastgoedobject.conditiescore}</td>
                  <td>
                    {vastgoedobject.datumafstoten ? (
                      <TextFormat type="date" value={vastgoedobject.datumafstoten} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {vastgoedobject.datumberekeningoppervlak ? (
                      <TextFormat type="date" value={vastgoedobject.datumberekeningoppervlak} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {vastgoedobject.datumeigendom ? (
                      <TextFormat type="date" value={vastgoedobject.datumeigendom} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {vastgoedobject.datumverkoop ? (
                      <TextFormat type="date" value={vastgoedobject.datumverkoop} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{vastgoedobject.deelportefeuille}</td>
                  <td>{vastgoedobject.energiekosten}</td>
                  <td>{vastgoedobject.energielabel}</td>
                  <td>{vastgoedobject.energieverbruik}</td>
                  <td>{vastgoedobject.fiscalewaarde}</td>
                  <td>{vastgoedobject.foto}</td>
                  <td>{vastgoedobject.gearchiveerd}</td>
                  <td>{vastgoedobject.herbouwwaarde}</td>
                  <td>{vastgoedobject.hoofdstuk}</td>
                  <td>{vastgoedobject.identificatie}</td>
                  <td>{vastgoedobject.jaarlaatsterenovatie}</td>
                  <td>{vastgoedobject.locatie}</td>
                  <td>{vastgoedobject.marktwaarde}</td>
                  <td>{vastgoedobject.monument}</td>
                  <td>{vastgoedobject.naam}</td>
                  <td>{vastgoedobject.eobjectstatus}</td>
                  <td>{vastgoedobject.eobjectstatuscode}</td>
                  <td>{vastgoedobject.eobjecttype}</td>
                  <td>{vastgoedobject.eobjecttypecode}</td>
                  <td>{vastgoedobject.omschrijving}</td>
                  <td>{vastgoedobject.onderhoudscategorie}</td>
                  <td>{vastgoedobject.oppervlaktekantoor}</td>
                  <td>{vastgoedobject.portefeuille}</td>
                  <td>{vastgoedobject.portefeuillecode}</td>
                  <td>{vastgoedobject.provincie}</td>
                  <td>{vastgoedobject.toelichting}</td>
                  <td>{vastgoedobject.verhuurbaarvloeroppervlak}</td>
                  <td>{vastgoedobject.verkoopbaarheid}</td>
                  <td>{vastgoedobject.verkoopbedrag}</td>
                  <td>{vastgoedobject.verzekerdewaarde}</td>
                  <td>{vastgoedobject.waardegrond}</td>
                  <td>{vastgoedobject.waardeopstal}</td>
                  <td>{vastgoedobject.wijk}</td>
                  <td>{vastgoedobject.wozwaarde}</td>
                  <td>
                    {vastgoedobject.betreftPand ? (
                      <Link to={`/pand/${vastgoedobject.betreftPand.id}`}>{vastgoedobject.betreftPand.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {vastgoedobject.heeftKostenplaats ? (
                      <Link to={`/kostenplaats/${vastgoedobject.heeftKostenplaats.id}`}>{vastgoedobject.heeftKostenplaats.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vastgoedobject/${vastgoedobject.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/vastgoedobject/${vastgoedobject.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vastgoedobject/${vastgoedobject.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Vastgoedobjects found</div>
        )}
      </div>
    </div>
  );
};

export default Vastgoedobject;
