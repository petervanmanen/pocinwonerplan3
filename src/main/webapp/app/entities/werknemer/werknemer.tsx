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

import { getEntities } from './werknemer.reducer';

export const Werknemer = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const werknemerList = useAppSelector(state => state.werknemer.entities);
  const loading = useAppSelector(state => state.werknemer.loading);

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
      <h2 id="werknemer-heading" data-cy="WerknemerHeading">
        Werknemers
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/werknemer/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Werknemer
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {werknemerList && werknemerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('geboortedatum')}>
                  Geboortedatum <FontAwesomeIcon icon={getSortIconByFieldName('geboortedatum')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('voornaam')}>
                  Voornaam <FontAwesomeIcon icon={getSortIconByFieldName('voornaam')} />
                </th>
                <th className="hand" onClick={sort('woonplaats')}>
                  Woonplaats <FontAwesomeIcon icon={getSortIconByFieldName('woonplaats')} />
                </th>
                <th>
                  Heeftondergaan Geweldsincident <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Rol <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Doetsollicitatiegesprek Sollicitatiegesprek <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {werknemerList.map((werknemer, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/werknemer/${werknemer.id}`} color="link" size="sm">
                      {werknemer.id}
                    </Button>
                  </td>
                  <td>
                    {werknemer.geboortedatum ? (
                      <TextFormat type="date" value={werknemer.geboortedatum} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{werknemer.naam}</td>
                  <td>{werknemer.voornaam}</td>
                  <td>{werknemer.woonplaats}</td>
                  <td>
                    {werknemer.heeftondergaanGeweldsincident ? (
                      <Link to={`/geweldsincident/${werknemer.heeftondergaanGeweldsincident.id}`}>
                        {werknemer.heeftondergaanGeweldsincident.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {werknemer.heeftRols
                      ? werknemer.heeftRols.map((val, j) => (
                          <span key={j}>
                            <Link to={`/rol/${val.id}`}>{val.id}</Link>
                            {j === werknemer.heeftRols.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {werknemer.doetsollicitatiegesprekSollicitatiegespreks
                      ? werknemer.doetsollicitatiegesprekSollicitatiegespreks.map((val, j) => (
                          <span key={j}>
                            <Link to={`/sollicitatiegesprek/${val.id}`}>{val.id}</Link>
                            {j === werknemer.doetsollicitatiegesprekSollicitatiegespreks.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/werknemer/${werknemer.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/werknemer/${werknemer.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/werknemer/${werknemer.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Werknemers found</div>
        )}
      </div>
    </div>
  );
};

export default Werknemer;
