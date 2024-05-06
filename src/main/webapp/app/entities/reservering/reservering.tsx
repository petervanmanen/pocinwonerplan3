import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './reservering.reducer';

export const Reservering = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const reserveringList = useAppSelector(state => state.reservering.entities);
  const loading = useAppSelector(state => state.reservering.loading);

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
      <h2 id="reservering-heading" data-cy="ReserveringHeading">
        Reserverings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/reservering/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Reservering
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {reserveringList && reserveringList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantal')}>
                  Aantal <FontAwesomeIcon icon={getSortIconByFieldName('aantal')} />
                </th>
                <th className="hand" onClick={sort('btw')}>
                  Btw <FontAwesomeIcon icon={getSortIconByFieldName('btw')} />
                </th>
                <th className="hand" onClick={sort('tijdtot')}>
                  Tijdtot <FontAwesomeIcon icon={getSortIconByFieldName('tijdtot')} />
                </th>
                <th className="hand" onClick={sort('tijdvanaf')}>
                  Tijdvanaf <FontAwesomeIcon icon={getSortIconByFieldName('tijdvanaf')} />
                </th>
                <th className="hand" onClick={sort('totaalprijs')}>
                  Totaalprijs <FontAwesomeIcon icon={getSortIconByFieldName('totaalprijs')} />
                </th>
                <th>
                  Betreft Voorziening <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Zaal <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Activiteit <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {reserveringList.map((reservering, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/reservering/${reservering.id}`} color="link" size="sm">
                      {reservering.id}
                    </Button>
                  </td>
                  <td>{reservering.aantal}</td>
                  <td>{reservering.btw}</td>
                  <td>{reservering.tijdtot}</td>
                  <td>{reservering.tijdvanaf}</td>
                  <td>{reservering.totaalprijs}</td>
                  <td>
                    {reservering.betreftVoorziening ? (
                      <Link to={`/voorziening/${reservering.betreftVoorziening.id}`}>{reservering.betreftVoorziening.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {reservering.betreftZaal ? <Link to={`/zaal/${reservering.betreftZaal.id}`}>{reservering.betreftZaal.id}</Link> : ''}
                  </td>
                  <td>
                    {reservering.heeftActiviteit ? (
                      <Link to={`/activiteit/${reservering.heeftActiviteit.id}`}>{reservering.heeftActiviteit.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/reservering/${reservering.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/reservering/${reservering.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/reservering/${reservering.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Reserverings found</div>
        )}
      </div>
    </div>
  );
};

export default Reservering;
