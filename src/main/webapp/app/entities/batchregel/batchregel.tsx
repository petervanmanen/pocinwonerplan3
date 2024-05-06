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

import { getEntities } from './batchregel.reducer';

export const Batchregel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const batchregelList = useAppSelector(state => state.batchregel.entities);
  const loading = useAppSelector(state => state.batchregel.loading);

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
      <h2 id="batchregel-heading" data-cy="BatchregelHeading">
        Batchregels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/batchregel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Batchregel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {batchregelList && batchregelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('bedrag')}>
                  Bedrag <FontAwesomeIcon icon={getSortIconByFieldName('bedrag')} />
                </th>
                <th className="hand" onClick={sort('datumbetaling')}>
                  Datumbetaling <FontAwesomeIcon icon={getSortIconByFieldName('datumbetaling')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('rekeningnaar')}>
                  Rekeningnaar <FontAwesomeIcon icon={getSortIconByFieldName('rekeningnaar')} />
                </th>
                <th className="hand" onClick={sort('rekeningvan')}>
                  Rekeningvan <FontAwesomeIcon icon={getSortIconByFieldName('rekeningvan')} />
                </th>
                <th>
                  Leidttot Mutatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Batch <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {batchregelList.map((batchregel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/batchregel/${batchregel.id}`} color="link" size="sm">
                      {batchregel.id}
                    </Button>
                  </td>
                  <td>{batchregel.bedrag}</td>
                  <td>
                    {batchregel.datumbetaling ? (
                      <TextFormat type="date" value={batchregel.datumbetaling} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{batchregel.omschrijving}</td>
                  <td>{batchregel.rekeningnaar}</td>
                  <td>{batchregel.rekeningvan}</td>
                  <td>
                    {batchregel.leidttotMutatie ? (
                      <Link to={`/mutatie/${batchregel.leidttotMutatie.id}`}>{batchregel.leidttotMutatie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>{batchregel.heeftBatch ? <Link to={`/batch/${batchregel.heeftBatch.id}`}>{batchregel.heeftBatch.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/batchregel/${batchregel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/batchregel/${batchregel.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/batchregel/${batchregel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Batchregels found</div>
        )}
      </div>
    </div>
  );
};

export default Batchregel;
