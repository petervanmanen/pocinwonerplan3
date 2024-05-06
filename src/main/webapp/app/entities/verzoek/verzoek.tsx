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

import { getEntities } from './verzoek.reducer';

export const Verzoek = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const verzoekList = useAppSelector(state => state.verzoek.entities);
  const loading = useAppSelector(state => state.verzoek.loading);

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
      <h2 id="verzoek-heading" data-cy="VerzoekHeading">
        Verzoeks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/verzoek/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Verzoek
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {verzoekList && verzoekList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('akkoordverklaring')}>
                  Akkoordverklaring <FontAwesomeIcon icon={getSortIconByFieldName('akkoordverklaring')} />
                </th>
                <th className="hand" onClick={sort('ambtshalve')}>
                  Ambtshalve <FontAwesomeIcon icon={getSortIconByFieldName('ambtshalve')} />
                </th>
                <th className="hand" onClick={sort('datumindiening')}>
                  Datumindiening <FontAwesomeIcon icon={getSortIconByFieldName('datumindiening')} />
                </th>
                <th className="hand" onClick={sort('doel')}>
                  Doel <FontAwesomeIcon icon={getSortIconByFieldName('doel')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('referentieaanvrager')}>
                  Referentieaanvrager <FontAwesomeIcon icon={getSortIconByFieldName('referentieaanvrager')} />
                </th>
                <th className="hand" onClick={sort('toelichtinglateraantelevereninformatie')}>
                  Toelichtinglateraantelevereninformatie{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('toelichtinglateraantelevereninformatie')} />
                </th>
                <th className="hand" onClick={sort('toelichtingnietaantelevereninformatie')}>
                  Toelichtingnietaantelevereninformatie{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('toelichtingnietaantelevereninformatie')} />
                </th>
                <th className="hand" onClick={sort('toelichtingverzoek')}>
                  Toelichtingverzoek <FontAwesomeIcon icon={getSortIconByFieldName('toelichtingverzoek')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('verzoeknummer')}>
                  Verzoeknummer <FontAwesomeIcon icon={getSortIconByFieldName('verzoeknummer')} />
                </th>
                <th className="hand" onClick={sort('volgnummer')}>
                  Volgnummer <FontAwesomeIcon icon={getSortIconByFieldName('volgnummer')} />
                </th>
                <th>
                  Leidttot Zaak <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betrefteerderverzoek Verzoek <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Projectactiviteit <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Projectlocatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Activiteit <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Locatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftalsverantwoordelijke Initiatiefnemer <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {verzoekList.map((verzoek, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/verzoek/${verzoek.id}`} color="link" size="sm">
                      {verzoek.id}
                    </Button>
                  </td>
                  <td>{verzoek.akkoordverklaring ? 'true' : 'false'}</td>
                  <td>{verzoek.ambtshalve ? 'true' : 'false'}</td>
                  <td>
                    {verzoek.datumindiening ? (
                      <TextFormat type="date" value={verzoek.datumindiening} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{verzoek.doel}</td>
                  <td>{verzoek.naam}</td>
                  <td>{verzoek.referentieaanvrager}</td>
                  <td>{verzoek.toelichtinglateraantelevereninformatie}</td>
                  <td>{verzoek.toelichtingnietaantelevereninformatie}</td>
                  <td>{verzoek.toelichtingverzoek}</td>
                  <td>{verzoek.type}</td>
                  <td>{verzoek.verzoeknummer}</td>
                  <td>{verzoek.volgnummer}</td>
                  <td>{verzoek.leidttotZaak ? <Link to={`/zaak/${verzoek.leidttotZaak.id}`}>{verzoek.leidttotZaak.id}</Link> : ''}</td>
                  <td>
                    {verzoek.betrefteerderverzoekVerzoek ? (
                      <Link to={`/verzoek/${verzoek.betrefteerderverzoekVerzoek.id}`}>{verzoek.betrefteerderverzoekVerzoek.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {verzoek.betreftProjectactiviteits
                      ? verzoek.betreftProjectactiviteits.map((val, j) => (
                          <span key={j}>
                            <Link to={`/projectactiviteit/${val.id}`}>{val.id}</Link>
                            {j === verzoek.betreftProjectactiviteits.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {verzoek.betreftProjectlocaties
                      ? verzoek.betreftProjectlocaties.map((val, j) => (
                          <span key={j}>
                            <Link to={`/projectlocatie/${val.id}`}>{val.id}</Link>
                            {j === verzoek.betreftProjectlocaties.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {verzoek.betreftActiviteits
                      ? verzoek.betreftActiviteits.map((val, j) => (
                          <span key={j}>
                            <Link to={`/activiteit/${val.id}`}>{val.id}</Link>
                            {j === verzoek.betreftActiviteits.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {verzoek.betreftLocaties
                      ? verzoek.betreftLocaties.map((val, j) => (
                          <span key={j}>
                            <Link to={`/locatie/${val.id}`}>{val.id}</Link>
                            {j === verzoek.betreftLocaties.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {verzoek.heeftalsverantwoordelijkeInitiatiefnemer ? (
                      <Link to={`/initiatiefnemer/${verzoek.heeftalsverantwoordelijkeInitiatiefnemer.id}`}>
                        {verzoek.heeftalsverantwoordelijkeInitiatiefnemer.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/verzoek/${verzoek.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/verzoek/${verzoek.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/verzoek/${verzoek.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Verzoeks found</div>
        )}
      </div>
    </div>
  );
};

export default Verzoek;
