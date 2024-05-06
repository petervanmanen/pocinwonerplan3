import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './normwaarde.reducer';

export const Normwaarde = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const normwaardeList = useAppSelector(state => state.normwaarde.entities);
  const loading = useAppSelector(state => state.normwaarde.loading);

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
      <h2 id="normwaarde-heading" data-cy="NormwaardeHeading">
        Normwaardes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/normwaarde/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Normwaarde
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {normwaardeList && normwaardeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('kwalitatievewaarde')}>
                  Kwalitatievewaarde <FontAwesomeIcon icon={getSortIconByFieldName('kwalitatievewaarde')} />
                </th>
                <th className="hand" onClick={sort('kwantitatievewaardeeenheid')}>
                  Kwantitatievewaardeeenheid <FontAwesomeIcon icon={getSortIconByFieldName('kwantitatievewaardeeenheid')} />
                </th>
                <th className="hand" onClick={sort('kwantitatievewaardeomvang')}>
                  Kwantitatievewaardeomvang <FontAwesomeIcon icon={getSortIconByFieldName('kwantitatievewaardeomvang')} />
                </th>
                <th>
                  Geldtvoor Locatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Bevat Norm <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {normwaardeList.map((normwaarde, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/normwaarde/${normwaarde.id}`} color="link" size="sm">
                      {normwaarde.id}
                    </Button>
                  </td>
                  <td>{normwaarde.kwalitatievewaarde}</td>
                  <td>{normwaarde.kwantitatievewaardeeenheid}</td>
                  <td>{normwaarde.kwantitatievewaardeomvang}</td>
                  <td>
                    {normwaarde.geldtvoorLocaties
                      ? normwaarde.geldtvoorLocaties.map((val, j) => (
                          <span key={j}>
                            <Link to={`/locatie/${val.id}`}>{val.id}</Link>
                            {j === normwaarde.geldtvoorLocaties.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>{normwaarde.bevatNorm ? <Link to={`/norm/${normwaarde.bevatNorm.id}`}>{normwaarde.bevatNorm.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/normwaarde/${normwaarde.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/normwaarde/${normwaarde.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/normwaarde/${normwaarde.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Normwaardes found</div>
        )}
      </div>
    </div>
  );
};

export default Normwaarde;
