import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './verkeerstelling.reducer';

export const Verkeerstelling = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const verkeerstellingList = useAppSelector(state => state.verkeerstelling.entities);
  const loading = useAppSelector(state => state.verkeerstelling.loading);

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
      <h2 id="verkeerstelling-heading" data-cy="VerkeerstellingHeading">
        Verkeerstellings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/verkeerstelling/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Verkeerstelling
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {verkeerstellingList && verkeerstellingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantal')}>
                  Aantal <FontAwesomeIcon icon={getSortIconByFieldName('aantal')} />
                </th>
                <th className="hand" onClick={sort('tijdtot')}>
                  Tijdtot <FontAwesomeIcon icon={getSortIconByFieldName('tijdtot')} />
                </th>
                <th className="hand" onClick={sort('tijdvanaf')}>
                  Tijdvanaf <FontAwesomeIcon icon={getSortIconByFieldName('tijdvanaf')} />
                </th>
                <th>
                  Gegenereerddoor Sensor <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {verkeerstellingList.map((verkeerstelling, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/verkeerstelling/${verkeerstelling.id}`} color="link" size="sm">
                      {verkeerstelling.id}
                    </Button>
                  </td>
                  <td>{verkeerstelling.aantal}</td>
                  <td>{verkeerstelling.tijdtot}</td>
                  <td>{verkeerstelling.tijdvanaf}</td>
                  <td>
                    {verkeerstelling.gegenereerddoorSensor ? (
                      <Link to={`/sensor/${verkeerstelling.gegenereerddoorSensor.id}`}>{verkeerstelling.gegenereerddoorSensor.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/verkeerstelling/${verkeerstelling.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/verkeerstelling/${verkeerstelling.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/verkeerstelling/${verkeerstelling.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Verkeerstellings found</div>
        )}
      </div>
    </div>
  );
};

export default Verkeerstelling;
