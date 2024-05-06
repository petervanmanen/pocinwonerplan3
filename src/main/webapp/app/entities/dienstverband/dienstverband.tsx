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

import { getEntities } from './dienstverband.reducer';

export const Dienstverband = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const dienstverbandList = useAppSelector(state => state.dienstverband.entities);
  const loading = useAppSelector(state => state.dienstverband.loading);

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
      <h2 id="dienstverband-heading" data-cy="DienstverbandHeading">
        Dienstverbands
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/dienstverband/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Dienstverband
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {dienstverbandList && dienstverbandList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('periodiek')}>
                  Periodiek <FontAwesomeIcon icon={getSortIconByFieldName('periodiek')} />
                </th>
                <th className="hand" onClick={sort('salaris')}>
                  Salaris <FontAwesomeIcon icon={getSortIconByFieldName('salaris')} />
                </th>
                <th className="hand" onClick={sort('schaal')}>
                  Schaal <FontAwesomeIcon icon={getSortIconByFieldName('schaal')} />
                </th>
                <th className="hand" onClick={sort('urenperweek')}>
                  Urenperweek <FontAwesomeIcon icon={getSortIconByFieldName('urenperweek')} />
                </th>
                <th>
                  Dienstverbandconformfunctie Functie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Aantalvolgensinzet Uren <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Medewerkerheeftdienstverband Werknemer <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Aantalvolgensinzet Inzet <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dienstverbandList.map((dienstverband, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/dienstverband/${dienstverband.id}`} color="link" size="sm">
                      {dienstverband.id}
                    </Button>
                  </td>
                  <td>
                    {dienstverband.datumeinde ? (
                      <TextFormat type="date" value={dienstverband.datumeinde} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {dienstverband.datumstart ? (
                      <TextFormat type="date" value={dienstverband.datumstart} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{dienstverband.periodiek}</td>
                  <td>{dienstverband.salaris}</td>
                  <td>{dienstverband.schaal}</td>
                  <td>{dienstverband.urenperweek}</td>
                  <td>
                    {dienstverband.dienstverbandconformfunctieFunctie ? (
                      <Link to={`/functie/${dienstverband.dienstverbandconformfunctieFunctie.id}`}>
                        {dienstverband.dienstverbandconformfunctieFunctie.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {dienstverband.aantalvolgensinzetUren ? (
                      <Link to={`/uren/${dienstverband.aantalvolgensinzetUren.id}`}>{dienstverband.aantalvolgensinzetUren.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {dienstverband.medewerkerheeftdienstverbandWerknemer ? (
                      <Link to={`/werknemer/${dienstverband.medewerkerheeftdienstverbandWerknemer.id}`}>
                        {dienstverband.medewerkerheeftdienstverbandWerknemer.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {dienstverband.aantalvolgensinzetInzet ? (
                      <Link to={`/inzet/${dienstverband.aantalvolgensinzetInzet.id}`}>{dienstverband.aantalvolgensinzetInzet.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/dienstverband/${dienstverband.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/dienstverband/${dienstverband.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/dienstverband/${dienstverband.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Dienstverbands found</div>
        )}
      </div>
    </div>
  );
};

export default Dienstverband;
