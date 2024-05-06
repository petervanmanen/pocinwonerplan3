import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './verplichtingwmojeugd.reducer';

export const Verplichtingwmojeugd = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const verplichtingwmojeugdList = useAppSelector(state => state.verplichtingwmojeugd.entities);
  const loading = useAppSelector(state => state.verplichtingwmojeugd.loading);

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
      <h2 id="verplichtingwmojeugd-heading" data-cy="VerplichtingwmojeugdHeading">
        Verplichtingwmojeugds
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/verplichtingwmojeugd/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Verplichtingwmojeugd
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {verplichtingwmojeugdList && verplichtingwmojeugdList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('budgetsoort')}>
                  Budgetsoort <FontAwesomeIcon icon={getSortIconByFieldName('budgetsoort')} />
                </th>
                <th className="hand" onClick={sort('budgetsoortgroep')}>
                  Budgetsoortgroep <FontAwesomeIcon icon={getSortIconByFieldName('budgetsoortgroep')} />
                </th>
                <th className="hand" onClick={sort('einddatumgepland')}>
                  Einddatumgepland <FontAwesomeIcon icon={getSortIconByFieldName('einddatumgepland')} />
                </th>
                <th className="hand" onClick={sort('feitelijkeeinddatum')}>
                  Feitelijkeeinddatum <FontAwesomeIcon icon={getSortIconByFieldName('feitelijkeeinddatum')} />
                </th>
                <th className="hand" onClick={sort('jaar')}>
                  Jaar <FontAwesomeIcon icon={getSortIconByFieldName('jaar')} />
                </th>
                <th className="hand" onClick={sort('periodiciteit')}>
                  Periodiciteit <FontAwesomeIcon icon={getSortIconByFieldName('periodiciteit')} />
                </th>
                <th className="hand" onClick={sort('verplichtingsoort')}>
                  Verplichtingsoort <FontAwesomeIcon icon={getSortIconByFieldName('verplichtingsoort')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {verplichtingwmojeugdList.map((verplichtingwmojeugd, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/verplichtingwmojeugd/${verplichtingwmojeugd.id}`} color="link" size="sm">
                      {verplichtingwmojeugd.id}
                    </Button>
                  </td>
                  <td>{verplichtingwmojeugd.budgetsoort}</td>
                  <td>{verplichtingwmojeugd.budgetsoortgroep}</td>
                  <td>{verplichtingwmojeugd.einddatumgepland}</td>
                  <td>{verplichtingwmojeugd.feitelijkeeinddatum}</td>
                  <td>{verplichtingwmojeugd.jaar}</td>
                  <td>{verplichtingwmojeugd.periodiciteit}</td>
                  <td>{verplichtingwmojeugd.verplichtingsoort}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/verplichtingwmojeugd/${verplichtingwmojeugd.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/verplichtingwmojeugd/${verplichtingwmojeugd.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/verplichtingwmojeugd/${verplichtingwmojeugd.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Verplichtingwmojeugds found</div>
        )}
      </div>
    </div>
  );
};

export default Verplichtingwmojeugd;
