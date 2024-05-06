import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './zaak.reducer';

export const Zaak = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const zaakList = useAppSelector(state => state.zaak.entities);
  const loading = useAppSelector(state => state.zaak.loading);

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
      <h2 id="zaak-heading" data-cy="ZaakHeading">
        Zaaks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/zaak/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Zaak
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {zaakList && zaakList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('empty')}>
                  Empty <FontAwesomeIcon icon={getSortIconByFieldName('empty')} />
                </th>
                <th className="hand" onClick={sort('archiefnominatie')}>
                  Archiefnominatie <FontAwesomeIcon icon={getSortIconByFieldName('archiefnominatie')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumeindegepland')}>
                  Datumeindegepland <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegepland')} />
                </th>
                <th className="hand" onClick={sort('datumeindeuiterlijkeafdoening')}>
                  Datumeindeuiterlijkeafdoening <FontAwesomeIcon icon={getSortIconByFieldName('datumeindeuiterlijkeafdoening')} />
                </th>
                <th className="hand" onClick={sort('datumlaatstebetaling')}>
                  Datumlaatstebetaling <FontAwesomeIcon icon={getSortIconByFieldName('datumlaatstebetaling')} />
                </th>
                <th className="hand" onClick={sort('datumpublicatie')}>
                  Datumpublicatie <FontAwesomeIcon icon={getSortIconByFieldName('datumpublicatie')} />
                </th>
                <th className="hand" onClick={sort('datumregistratie')}>
                  Datumregistratie <FontAwesomeIcon icon={getSortIconByFieldName('datumregistratie')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('datumvernietigingdossier')}>
                  Datumvernietigingdossier <FontAwesomeIcon icon={getSortIconByFieldName('datumvernietigingdossier')} />
                </th>
                <th className="hand" onClick={sort('document')}>
                  Document <FontAwesomeIcon icon={getSortIconByFieldName('document')} />
                </th>
                <th className="hand" onClick={sort('duurverlenging')}>
                  Duurverlenging <FontAwesomeIcon icon={getSortIconByFieldName('duurverlenging')} />
                </th>
                <th className="hand" onClick={sort('indicatiebetaling')}>
                  Indicatiebetaling <FontAwesomeIcon icon={getSortIconByFieldName('indicatiebetaling')} />
                </th>
                <th className="hand" onClick={sort('indicatiedeelzaken')}>
                  Indicatiedeelzaken <FontAwesomeIcon icon={getSortIconByFieldName('indicatiedeelzaken')} />
                </th>
                <th className="hand" onClick={sort('indicatieopschorting')}>
                  Indicatieopschorting <FontAwesomeIcon icon={getSortIconByFieldName('indicatieopschorting')} />
                </th>
                <th className="hand" onClick={sort('leges')}>
                  Leges <FontAwesomeIcon icon={getSortIconByFieldName('leges')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('omschrijvingresultaat')}>
                  Omschrijvingresultaat <FontAwesomeIcon icon={getSortIconByFieldName('omschrijvingresultaat')} />
                </th>
                <th className="hand" onClick={sort('redenopschorting')}>
                  Redenopschorting <FontAwesomeIcon icon={getSortIconByFieldName('redenopschorting')} />
                </th>
                <th className="hand" onClick={sort('redenverlenging')}>
                  Redenverlenging <FontAwesomeIcon icon={getSortIconByFieldName('redenverlenging')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th className="hand" onClick={sort('toelichtingresultaat')}>
                  Toelichtingresultaat <FontAwesomeIcon icon={getSortIconByFieldName('toelichtingresultaat')} />
                </th>
                <th className="hand" onClick={sort('vertrouwelijkheid')}>
                  Vertrouwelijkheid <FontAwesomeIcon icon={getSortIconByFieldName('vertrouwelijkheid')} />
                </th>
                <th className="hand" onClick={sort('zaakidentificatie')}>
                  Zaakidentificatie <FontAwesomeIcon icon={getSortIconByFieldName('zaakidentificatie')} />
                </th>
                <th className="hand" onClick={sort('zaakniveau')}>
                  Zaakniveau <FontAwesomeIcon icon={getSortIconByFieldName('zaakniveau')} />
                </th>
                <th>
                  Heeftproduct Producttype <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Klantbeoordeling <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Heffing <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Project <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isvan Zaaktype <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Kent Document <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Afhandelendmedewerker Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Grondslag <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Uitgevoerdbinnen Bedrijfsproces <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Oefentuit Betrokkene <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {zaakList.map((zaak, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/zaak/${zaak.id}`} color="link" size="sm">
                      {zaak.id}
                    </Button>
                  </td>
                  <td>{zaak.empty}</td>
                  <td>{zaak.archiefnominatie}</td>
                  <td>{zaak.datumeinde}</td>
                  <td>{zaak.datumeindegepland}</td>
                  <td>{zaak.datumeindeuiterlijkeafdoening}</td>
                  <td>{zaak.datumlaatstebetaling}</td>
                  <td>{zaak.datumpublicatie}</td>
                  <td>{zaak.datumregistratie}</td>
                  <td>{zaak.datumstart}</td>
                  <td>{zaak.datumvernietigingdossier}</td>
                  <td>{zaak.document}</td>
                  <td>{zaak.duurverlenging}</td>
                  <td>{zaak.indicatiebetaling}</td>
                  <td>{zaak.indicatiedeelzaken}</td>
                  <td>{zaak.indicatieopschorting}</td>
                  <td>{zaak.leges}</td>
                  <td>{zaak.omschrijving}</td>
                  <td>{zaak.omschrijvingresultaat}</td>
                  <td>{zaak.redenopschorting}</td>
                  <td>{zaak.redenverlenging}</td>
                  <td>{zaak.toelichting}</td>
                  <td>{zaak.toelichtingresultaat}</td>
                  <td>{zaak.vertrouwelijkheid}</td>
                  <td>{zaak.zaakidentificatie}</td>
                  <td>{zaak.zaakniveau}</td>
                  <td>
                    {zaak.heeftproductProducttype ? (
                      <Link to={`/producttype/${zaak.heeftproductProducttype.id}`}>{zaak.heeftproductProducttype.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {zaak.heeftKlantbeoordeling ? (
                      <Link to={`/klantbeoordeling/${zaak.heeftKlantbeoordeling.id}`}>{zaak.heeftKlantbeoordeling.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{zaak.heeftHeffing ? <Link to={`/heffing/${zaak.heeftHeffing.id}`}>{zaak.heeftHeffing.id}</Link> : ''}</td>
                  <td>{zaak.betreftProject ? <Link to={`/project/${zaak.betreftProject.id}`}>{zaak.betreftProject.id}</Link> : ''}</td>
                  <td>{zaak.isvanZaaktype ? <Link to={`/zaaktype/${zaak.isvanZaaktype.id}`}>{zaak.isvanZaaktype.id}</Link> : ''}</td>
                  <td>
                    {zaak.kentDocuments
                      ? zaak.kentDocuments.map((val, j) => (
                          <span key={j}>
                            <Link to={`/document/${val.id}`}>{val.id}</Link>
                            {j === zaak.kentDocuments.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {zaak.afhandelendmedewerkerMedewerkers
                      ? zaak.afhandelendmedewerkerMedewerkers.map((val, j) => (
                          <span key={j}>
                            <Link to={`/medewerker/${val.id}`}>{val.id}</Link>
                            {j === zaak.afhandelendmedewerkerMedewerkers.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {zaak.heeftGrondslags
                      ? zaak.heeftGrondslags.map((val, j) => (
                          <span key={j}>
                            <Link to={`/grondslag/${val.id}`}>{val.id}</Link>
                            {j === zaak.heeftGrondslags.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {zaak.uitgevoerdbinnenBedrijfsproces
                      ? zaak.uitgevoerdbinnenBedrijfsproces.map((val, j) => (
                          <span key={j}>
                            <Link to={`/bedrijfsproces/${val.id}`}>{val.id}</Link>
                            {j === zaak.uitgevoerdbinnenBedrijfsproces.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {zaak.oefentuitBetrokkenes
                      ? zaak.oefentuitBetrokkenes.map((val, j) => (
                          <span key={j}>
                            <Link to={`/betrokkene/${val.id}`}>{val.id}</Link>
                            {j === zaak.oefentuitBetrokkenes.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/zaak/${zaak.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/zaak/${zaak.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/zaak/${zaak.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Zaaks found</div>
        )}
      </div>
    </div>
  );
};

export default Zaak;
