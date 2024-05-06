import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './doos.reducer';

export const Doos = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const doosList = useAppSelector(state => state.doos.entities);
  const loading = useAppSelector(state => state.doos.loading);

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
      <h2 id="doos-heading" data-cy="DoosHeading">
        Doos
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/doos/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Doos
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {doosList && doosList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('doosnummer')}>
                  Doosnummer <FontAwesomeIcon icon={getSortIconByFieldName('doosnummer')} />
                </th>
                <th className="hand" onClick={sort('herkomst')}>
                  Herkomst <FontAwesomeIcon icon={getSortIconByFieldName('herkomst')} />
                </th>
                <th className="hand" onClick={sort('inhoud')}>
                  Inhoud <FontAwesomeIcon icon={getSortIconByFieldName('inhoud')} />
                </th>
                <th className="hand" onClick={sort('key')}>
                  Key <FontAwesomeIcon icon={getSortIconByFieldName('key')} />
                </th>
                <th className="hand" onClick={sort('keymagazijnlocatie')}>
                  Keymagazijnlocatie <FontAwesomeIcon icon={getSortIconByFieldName('keymagazijnlocatie')} />
                </th>
                <th className="hand" onClick={sort('projectcd')}>
                  Projectcd <FontAwesomeIcon icon={getSortIconByFieldName('projectcd')} />
                </th>
                <th>
                  Staatop Magazijnlocatie <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {doosList.map((doos, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/doos/${doos.id}`} color="link" size="sm">
                      {doos.id}
                    </Button>
                  </td>
                  <td>{doos.doosnummer}</td>
                  <td>{doos.herkomst}</td>
                  <td>{doos.inhoud}</td>
                  <td>{doos.key}</td>
                  <td>{doos.keymagazijnlocatie}</td>
                  <td>{doos.projectcd}</td>
                  <td>
                    {doos.staatopMagazijnlocatie ? (
                      <Link to={`/magazijnlocatie/${doos.staatopMagazijnlocatie.id}`}>{doos.staatopMagazijnlocatie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/doos/${doos.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/doos/${doos.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/doos/${doos.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Doos found</div>
        )}
      </div>
    </div>
  );
};

export default Doos;
