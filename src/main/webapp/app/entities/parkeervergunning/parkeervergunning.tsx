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

import { getEntities } from './parkeervergunning.reducer';

export const Parkeervergunning = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const parkeervergunningList = useAppSelector(state => state.parkeervergunning.entities);
  const loading = useAppSelector(state => state.parkeervergunning.loading);

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
      <h2 id="parkeervergunning-heading" data-cy="ParkeervergunningHeading">
        Parkeervergunnings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/parkeervergunning/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Parkeervergunning
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {parkeervergunningList && parkeervergunningList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheid')}>
                  Datumeindegeldigheid <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheid')} />
                </th>
                <th className="hand" onClick={sort('datumreservering')}>
                  Datumreservering <FontAwesomeIcon icon={getSortIconByFieldName('datumreservering')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('kenteken')}>
                  Kenteken <FontAwesomeIcon icon={getSortIconByFieldName('kenteken')} />
                </th>
                <th className="hand" onClick={sort('minutenafgeschreven')}>
                  Minutenafgeschreven <FontAwesomeIcon icon={getSortIconByFieldName('minutenafgeschreven')} />
                </th>
                <th className="hand" onClick={sort('minutengeldig')}>
                  Minutengeldig <FontAwesomeIcon icon={getSortIconByFieldName('minutengeldig')} />
                </th>
                <th className="hand" onClick={sort('minutenresterend')}>
                  Minutenresterend <FontAwesomeIcon icon={getSortIconByFieldName('minutenresterend')} />
                </th>
                <th className="hand" onClick={sort('nummer')}>
                  Nummer <FontAwesomeIcon icon={getSortIconByFieldName('nummer')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th>
                  Resulteert Parkeerrecht <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Houder Rechtspersoon <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Soort Productgroep <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Soort Productsoort <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {parkeervergunningList.map((parkeervergunning, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/parkeervergunning/${parkeervergunning.id}`} color="link" size="sm">
                      {parkeervergunning.id}
                    </Button>
                  </td>
                  <td>{parkeervergunning.datumeindegeldigheid}</td>
                  <td>
                    {parkeervergunning.datumreservering ? (
                      <TextFormat type="date" value={parkeervergunning.datumreservering} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{parkeervergunning.datumstart}</td>
                  <td>{parkeervergunning.kenteken}</td>
                  <td>{parkeervergunning.minutenafgeschreven}</td>
                  <td>{parkeervergunning.minutengeldig}</td>
                  <td>{parkeervergunning.minutenresterend}</td>
                  <td>{parkeervergunning.nummer}</td>
                  <td>{parkeervergunning.type}</td>
                  <td>
                    {parkeervergunning.resulteertParkeerrecht ? (
                      <Link to={`/parkeerrecht/${parkeervergunning.resulteertParkeerrecht.id}`}>
                        {parkeervergunning.resulteertParkeerrecht.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {parkeervergunning.houderRechtspersoon ? (
                      <Link to={`/rechtspersoon/${parkeervergunning.houderRechtspersoon.id}`}>
                        {parkeervergunning.houderRechtspersoon.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {parkeervergunning.soortProductgroep ? (
                      <Link to={`/productgroep/${parkeervergunning.soortProductgroep.id}`}>{parkeervergunning.soortProductgroep.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {parkeervergunning.soortProductsoort ? (
                      <Link to={`/productsoort/${parkeervergunning.soortProductsoort.id}`}>{parkeervergunning.soortProductsoort.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/parkeervergunning/${parkeervergunning.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/parkeervergunning/${parkeervergunning.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/parkeervergunning/${parkeervergunning.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Parkeervergunnings found</div>
        )}
      </div>
    </div>
  );
};

export default Parkeervergunning;
