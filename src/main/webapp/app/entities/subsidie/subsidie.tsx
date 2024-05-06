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

import { getEntities } from './subsidie.reducer';

export const Subsidie = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const subsidieList = useAppSelector(state => state.subsidie.entities);
  const loading = useAppSelector(state => state.subsidie.loading);

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
      <h2 id="subsidie-heading" data-cy="SubsidieHeading">
        Subsidies
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/subsidie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Subsidie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {subsidieList && subsidieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('accountantscontrole')}>
                  Accountantscontrole <FontAwesomeIcon icon={getSortIconByFieldName('accountantscontrole')} />
                </th>
                <th className="hand" onClick={sort('cofinanciering')}>
                  Cofinanciering <FontAwesomeIcon icon={getSortIconByFieldName('cofinanciering')} />
                </th>
                <th className="hand" onClick={sort('datumbehandeltermijn')}>
                  Datumbehandeltermijn <FontAwesomeIcon icon={getSortIconByFieldName('datumbehandeltermijn')} />
                </th>
                <th className="hand" onClick={sort('datumbewaartermijn')}>
                  Datumbewaartermijn <FontAwesomeIcon icon={getSortIconByFieldName('datumbewaartermijn')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('datumsubsidievaststelling')}>
                  Datumsubsidievaststelling <FontAwesomeIcon icon={getSortIconByFieldName('datumsubsidievaststelling')} />
                </th>
                <th className="hand" onClick={sort('datumverzendingeindeafrekening')}>
                  Datumverzendingeindeafrekening <FontAwesomeIcon icon={getSortIconByFieldName('datumverzendingeindeafrekening')} />
                </th>
                <th className="hand" onClick={sort('deadlineindiening')}>
                  Deadlineindiening <FontAwesomeIcon icon={getSortIconByFieldName('deadlineindiening')} />
                </th>
                <th className="hand" onClick={sort('doelstelling')}>
                  Doelstelling <FontAwesomeIcon icon={getSortIconByFieldName('doelstelling')} />
                </th>
                <th className="hand" onClick={sort('gerealiseerdeprojectkosten')}>
                  Gerealiseerdeprojectkosten <FontAwesomeIcon icon={getSortIconByFieldName('gerealiseerdeprojectkosten')} />
                </th>
                <th className="hand" onClick={sort('hoogtesubsidie')}>
                  Hoogtesubsidie <FontAwesomeIcon icon={getSortIconByFieldName('hoogtesubsidie')} />
                </th>
                <th className="hand" onClick={sort('niveau')}>
                  Niveau <FontAwesomeIcon icon={getSortIconByFieldName('niveau')} />
                </th>
                <th className="hand" onClick={sort('onderwerp')}>
                  Onderwerp <FontAwesomeIcon icon={getSortIconByFieldName('onderwerp')} />
                </th>
                <th className="hand" onClick={sort('ontvangenbedrag')}>
                  Ontvangenbedrag <FontAwesomeIcon icon={getSortIconByFieldName('ontvangenbedrag')} />
                </th>
                <th className="hand" onClick={sort('opmerkingen')}>
                  Opmerkingen <FontAwesomeIcon icon={getSortIconByFieldName('opmerkingen')} />
                </th>
                <th className="hand" onClick={sort('opmerkingenvoorschotten')}>
                  Opmerkingenvoorschotten <FontAwesomeIcon icon={getSortIconByFieldName('opmerkingenvoorschotten')} />
                </th>
                <th className="hand" onClick={sort('prestatiesubsidie')}>
                  Prestatiesubsidie <FontAwesomeIcon icon={getSortIconByFieldName('prestatiesubsidie')} />
                </th>
                <th className="hand" onClick={sort('socialreturnbedrag')}>
                  Socialreturnbedrag <FontAwesomeIcon icon={getSortIconByFieldName('socialreturnbedrag')} />
                </th>
                <th className="hand" onClick={sort('socialreturnnagekomen')}>
                  Socialreturnnagekomen <FontAwesomeIcon icon={getSortIconByFieldName('socialreturnnagekomen')} />
                </th>
                <th className="hand" onClick={sort('socialreturnverplichting')}>
                  Socialreturnverplichting <FontAwesomeIcon icon={getSortIconByFieldName('socialreturnverplichting')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('subsidiebedrag')}>
                  Subsidiebedrag <FontAwesomeIcon icon={getSortIconByFieldName('subsidiebedrag')} />
                </th>
                <th className="hand" onClick={sort('subsidiesoort')}>
                  Subsidiesoort <FontAwesomeIcon icon={getSortIconByFieldName('subsidiesoort')} />
                </th>
                <th className="hand" onClick={sort('subsidievaststellingbedrag')}>
                  Subsidievaststellingbedrag <FontAwesomeIcon icon={getSortIconByFieldName('subsidievaststellingbedrag')} />
                </th>
                <th className="hand" onClick={sort('uitgaandesubsidie')}>
                  Uitgaandesubsidie <FontAwesomeIcon icon={getSortIconByFieldName('uitgaandesubsidie')} />
                </th>
                <th className="hand" onClick={sort('verantwoordenop')}>
                  Verantwoordenop <FontAwesomeIcon icon={getSortIconByFieldName('verantwoordenop')} />
                </th>
                <th>
                  Heeft Zaak <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Valtbinnen Sector <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Behandelaar Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Verstrekker Rechtspersoon <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Kostenplaats <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Document <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Aanvrager Rechtspersoon <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Aanvrager Medewerker <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {subsidieList.map((subsidie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/subsidie/${subsidie.id}`} color="link" size="sm">
                      {subsidie.id}
                    </Button>
                  </td>
                  <td>{subsidie.accountantscontrole}</td>
                  <td>{subsidie.cofinanciering}</td>
                  <td>
                    {subsidie.datumbehandeltermijn ? (
                      <TextFormat type="date" value={subsidie.datumbehandeltermijn} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {subsidie.datumbewaartermijn ? (
                      <TextFormat type="date" value={subsidie.datumbewaartermijn} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {subsidie.datumeinde ? <TextFormat type="date" value={subsidie.datumeinde} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {subsidie.datumstart ? <TextFormat type="date" value={subsidie.datumstart} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {subsidie.datumsubsidievaststelling ? (
                      <TextFormat type="date" value={subsidie.datumsubsidievaststelling} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {subsidie.datumverzendingeindeafrekening ? (
                      <TextFormat type="date" value={subsidie.datumverzendingeindeafrekening} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {subsidie.deadlineindiening ? (
                      <TextFormat type="date" value={subsidie.deadlineindiening} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{subsidie.doelstelling}</td>
                  <td>
                    {subsidie.gerealiseerdeprojectkosten ? (
                      <TextFormat type="date" value={subsidie.gerealiseerdeprojectkosten} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{subsidie.hoogtesubsidie}</td>
                  <td>{subsidie.niveau}</td>
                  <td>{subsidie.onderwerp}</td>
                  <td>{subsidie.ontvangenbedrag}</td>
                  <td>{subsidie.opmerkingen}</td>
                  <td>{subsidie.opmerkingenvoorschotten}</td>
                  <td>{subsidie.prestatiesubsidie}</td>
                  <td>{subsidie.socialreturnbedrag}</td>
                  <td>{subsidie.socialreturnnagekomen}</td>
                  <td>{subsidie.socialreturnverplichting}</td>
                  <td>{subsidie.status}</td>
                  <td>{subsidie.subsidiebedrag}</td>
                  <td>{subsidie.subsidiesoort}</td>
                  <td>{subsidie.subsidievaststellingbedrag}</td>
                  <td>{subsidie.uitgaandesubsidie}</td>
                  <td>
                    {subsidie.verantwoordenop ? (
                      <TextFormat type="date" value={subsidie.verantwoordenop} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{subsidie.heeftZaak ? <Link to={`/zaak/${subsidie.heeftZaak.id}`}>{subsidie.heeftZaak.id}</Link> : ''}</td>
                  <td>
                    {subsidie.valtbinnenSector ? (
                      <Link to={`/sector/${subsidie.valtbinnenSector.id}`}>{subsidie.valtbinnenSector.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {subsidie.behandelaarMedewerker ? (
                      <Link to={`/medewerker/${subsidie.behandelaarMedewerker.id}`}>{subsidie.behandelaarMedewerker.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {subsidie.verstrekkerRechtspersoon ? (
                      <Link to={`/rechtspersoon/${subsidie.verstrekkerRechtspersoon.id}`}>{subsidie.verstrekkerRechtspersoon.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {subsidie.heeftKostenplaats ? (
                      <Link to={`/kostenplaats/${subsidie.heeftKostenplaats.id}`}>{subsidie.heeftKostenplaats.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {subsidie.heeftDocument ? <Link to={`/document/${subsidie.heeftDocument.id}`}>{subsidie.heeftDocument.id}</Link> : ''}
                  </td>
                  <td>
                    {subsidie.aanvragerRechtspersoon ? (
                      <Link to={`/rechtspersoon/${subsidie.aanvragerRechtspersoon.id}`}>{subsidie.aanvragerRechtspersoon.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {subsidie.aanvragerMedewerker ? (
                      <Link to={`/medewerker/${subsidie.aanvragerMedewerker.id}`}>{subsidie.aanvragerMedewerker.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/subsidie/${subsidie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/subsidie/${subsidie.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/subsidie/${subsidie.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Subsidies found</div>
        )}
      </div>
    </div>
  );
};

export default Subsidie;
