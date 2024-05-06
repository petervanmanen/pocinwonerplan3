import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './fraudegegevens.reducer';

export const Fraudegegevens = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const fraudegegevensList = useAppSelector(state => state.fraudegegevens.entities);
  const loading = useAppSelector(state => state.fraudegegevens.loading);

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
      <h2 id="fraudegegevens-heading" data-cy="FraudegegevensHeading">
        Fraudegegevens
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/fraudegegevens/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Fraudegegevens
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {fraudegegevensList && fraudegegevensList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('bedragfraude')}>
                  Bedragfraude <FontAwesomeIcon icon={getSortIconByFieldName('bedragfraude')} />
                </th>
                <th className="hand" onClick={sort('datumeindefraude')}>
                  Datumeindefraude <FontAwesomeIcon icon={getSortIconByFieldName('datumeindefraude')} />
                </th>
                <th className="hand" onClick={sort('datumgeconstateerd')}>
                  Datumgeconstateerd <FontAwesomeIcon icon={getSortIconByFieldName('datumgeconstateerd')} />
                </th>
                <th className="hand" onClick={sort('datumstartfraude')}>
                  Datumstartfraude <FontAwesomeIcon icon={getSortIconByFieldName('datumstartfraude')} />
                </th>
                <th className="hand" onClick={sort('verrekening')}>
                  Verrekening <FontAwesomeIcon icon={getSortIconByFieldName('verrekening')} />
                </th>
                <th className="hand" onClick={sort('vorderingen')}>
                  Vorderingen <FontAwesomeIcon icon={getSortIconByFieldName('vorderingen')} />
                </th>
                <th>
                  Isvansoort Fraudesoort <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftfraudegegevens Client <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {fraudegegevensList.map((fraudegegevens, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/fraudegegevens/${fraudegegevens.id}`} color="link" size="sm">
                      {fraudegegevens.id}
                    </Button>
                  </td>
                  <td>{fraudegegevens.bedragfraude}</td>
                  <td>{fraudegegevens.datumeindefraude}</td>
                  <td>{fraudegegevens.datumgeconstateerd}</td>
                  <td>{fraudegegevens.datumstartfraude}</td>
                  <td>{fraudegegevens.verrekening}</td>
                  <td>{fraudegegevens.vorderingen}</td>
                  <td>
                    {fraudegegevens.isvansoortFraudesoort ? (
                      <Link to={`/fraudesoort/${fraudegegevens.isvansoortFraudesoort.id}`}>{fraudegegevens.isvansoortFraudesoort.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {fraudegegevens.heeftfraudegegevensClient ? (
                      <Link to={`/client/${fraudegegevens.heeftfraudegegevensClient.id}`}>
                        {fraudegegevens.heeftfraudegegevensClient.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/fraudegegevens/${fraudegegevens.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/fraudegegevens/${fraudegegevens.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/fraudegegevens/${fraudegegevens.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Fraudegegevens found</div>
        )}
      </div>
    </div>
  );
};

export default Fraudegegevens;
