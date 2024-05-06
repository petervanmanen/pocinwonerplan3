import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './winkelvloeroppervlak.reducer';

export const Winkelvloeroppervlak = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const winkelvloeroppervlakList = useAppSelector(state => state.winkelvloeroppervlak.entities);
  const loading = useAppSelector(state => state.winkelvloeroppervlak.loading);

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
      <h2 id="winkelvloeroppervlak-heading" data-cy="WinkelvloeroppervlakHeading">
        Winkelvloeroppervlaks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/winkelvloeroppervlak/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Winkelvloeroppervlak
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {winkelvloeroppervlakList && winkelvloeroppervlakList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantalkassa')}>
                  Aantalkassa <FontAwesomeIcon icon={getSortIconByFieldName('aantalkassa')} />
                </th>
                <th className="hand" onClick={sort('bronwvo')}>
                  Bronwvo <FontAwesomeIcon icon={getSortIconByFieldName('bronwvo')} />
                </th>
                <th className="hand" onClick={sort('leegstand')}>
                  Leegstand <FontAwesomeIcon icon={getSortIconByFieldName('leegstand')} />
                </th>
                <th className="hand" onClick={sort('winkelvloeroppervlakte')}>
                  Winkelvloeroppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('winkelvloeroppervlakte')} />
                </th>
                <th className="hand" onClick={sort('wvoklasse')}>
                  Wvoklasse <FontAwesomeIcon icon={getSortIconByFieldName('wvoklasse')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {winkelvloeroppervlakList.map((winkelvloeroppervlak, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/winkelvloeroppervlak/${winkelvloeroppervlak.id}`} color="link" size="sm">
                      {winkelvloeroppervlak.id}
                    </Button>
                  </td>
                  <td>{winkelvloeroppervlak.aantalkassa}</td>
                  <td>{winkelvloeroppervlak.bronwvo}</td>
                  <td>{winkelvloeroppervlak.leegstand}</td>
                  <td>{winkelvloeroppervlak.winkelvloeroppervlakte}</td>
                  <td>{winkelvloeroppervlak.wvoklasse}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/winkelvloeroppervlak/${winkelvloeroppervlak.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/winkelvloeroppervlak/${winkelvloeroppervlak.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/winkelvloeroppervlak/${winkelvloeroppervlak.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Winkelvloeroppervlaks found</div>
        )}
      </div>
    </div>
  );
};

export default Winkelvloeroppervlak;
