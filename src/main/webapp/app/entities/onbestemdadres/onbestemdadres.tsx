import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './onbestemdadres.reducer';

export const Onbestemdadres = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const onbestemdadresList = useAppSelector(state => state.onbestemdadres.entities);
  const loading = useAppSelector(state => state.onbestemdadres.loading);

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
      <h2 id="onbestemdadres-heading" data-cy="OnbestemdadresHeading">
        Onbestemdadres
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/onbestemdadres/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Onbestemdadres
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {onbestemdadresList && onbestemdadresList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('huisletter')}>
                  Huisletter <FontAwesomeIcon icon={getSortIconByFieldName('huisletter')} />
                </th>
                <th className="hand" onClick={sort('huisnummer')}>
                  Huisnummer <FontAwesomeIcon icon={getSortIconByFieldName('huisnummer')} />
                </th>
                <th className="hand" onClick={sort('huisnummertoevoeging')}>
                  Huisnummertoevoeging <FontAwesomeIcon icon={getSortIconByFieldName('huisnummertoevoeging')} />
                </th>
                <th className="hand" onClick={sort('postcode')}>
                  Postcode <FontAwesomeIcon icon={getSortIconByFieldName('postcode')} />
                </th>
                <th className="hand" onClick={sort('straatnaam')}>
                  Straatnaam <FontAwesomeIcon icon={getSortIconByFieldName('straatnaam')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {onbestemdadresList.map((onbestemdadres, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/onbestemdadres/${onbestemdadres.id}`} color="link" size="sm">
                      {onbestemdadres.id}
                    </Button>
                  </td>
                  <td>{onbestemdadres.huisletter}</td>
                  <td>{onbestemdadres.huisnummer}</td>
                  <td>{onbestemdadres.huisnummertoevoeging}</td>
                  <td>{onbestemdadres.postcode}</td>
                  <td>{onbestemdadres.straatnaam}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/onbestemdadres/${onbestemdadres.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/onbestemdadres/${onbestemdadres.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/onbestemdadres/${onbestemdadres.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Onbestemdadres found</div>
        )}
      </div>
    </div>
  );
};

export default Onbestemdadres;
