import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './ecomponentsoort.reducer';

export const Ecomponentsoort = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const ecomponentsoortList = useAppSelector(state => state.ecomponentsoort.entities);
  const loading = useAppSelector(state => state.ecomponentsoort.loading);

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
      <h2 id="ecomponentsoort-heading" data-cy="EcomponentsoortHeading">
        Ecomponentsoorts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/ecomponentsoort/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Ecomponentsoort
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ecomponentsoortList && ecomponentsoortList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('ecomponent')}>
                  Ecomponent <FontAwesomeIcon icon={getSortIconByFieldName('ecomponent')} />
                </th>
                <th className="hand" onClick={sort('ecomponentcode')}>
                  Ecomponentcode <FontAwesomeIcon icon={getSortIconByFieldName('ecomponentcode')} />
                </th>
                <th className="hand" onClick={sort('kolom')}>
                  Kolom <FontAwesomeIcon icon={getSortIconByFieldName('kolom')} />
                </th>
                <th className="hand" onClick={sort('kolomcode')}>
                  Kolomcode <FontAwesomeIcon icon={getSortIconByFieldName('kolomcode')} />
                </th>
                <th className="hand" onClick={sort('regeling')}>
                  Regeling <FontAwesomeIcon icon={getSortIconByFieldName('regeling')} />
                </th>
                <th className="hand" onClick={sort('regelingcode')}>
                  Regelingcode <FontAwesomeIcon icon={getSortIconByFieldName('regelingcode')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ecomponentsoortList.map((ecomponentsoort, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/ecomponentsoort/${ecomponentsoort.id}`} color="link" size="sm">
                      {ecomponentsoort.id}
                    </Button>
                  </td>
                  <td>{ecomponentsoort.ecomponent}</td>
                  <td>{ecomponentsoort.ecomponentcode}</td>
                  <td>{ecomponentsoort.kolom}</td>
                  <td>{ecomponentsoort.kolomcode}</td>
                  <td>{ecomponentsoort.regeling}</td>
                  <td>{ecomponentsoort.regelingcode}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/ecomponentsoort/${ecomponentsoort.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/ecomponentsoort/${ecomponentsoort.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/ecomponentsoort/${ecomponentsoort.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Ecomponentsoorts found</div>
        )}
      </div>
    </div>
  );
};

export default Ecomponentsoort;
