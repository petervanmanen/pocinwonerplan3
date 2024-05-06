import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './vulling.reducer';

export const Vulling = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vullingList = useAppSelector(state => state.vulling.entities);
  const loading = useAppSelector(state => state.vulling.loading);

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
      <h2 id="vulling-heading" data-cy="VullingHeading">
        Vullings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vulling/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vulling
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vullingList && vullingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('grondsoort')}>
                  Grondsoort <FontAwesomeIcon icon={getSortIconByFieldName('grondsoort')} />
                </th>
                <th className="hand" onClick={sort('key')}>
                  Key <FontAwesomeIcon icon={getSortIconByFieldName('key')} />
                </th>
                <th className="hand" onClick={sort('keyspoor')}>
                  Keyspoor <FontAwesomeIcon icon={getSortIconByFieldName('keyspoor')} />
                </th>
                <th className="hand" onClick={sort('kleur')}>
                  Kleur <FontAwesomeIcon icon={getSortIconByFieldName('kleur')} />
                </th>
                <th className="hand" onClick={sort('projectcd')}>
                  Projectcd <FontAwesomeIcon icon={getSortIconByFieldName('projectcd')} />
                </th>
                <th className="hand" onClick={sort('putnummer')}>
                  Putnummer <FontAwesomeIcon icon={getSortIconByFieldName('putnummer')} />
                </th>
                <th className="hand" onClick={sort('spoornummer')}>
                  Spoornummer <FontAwesomeIcon icon={getSortIconByFieldName('spoornummer')} />
                </th>
                <th className="hand" onClick={sort('structuur')}>
                  Structuur <FontAwesomeIcon icon={getSortIconByFieldName('structuur')} />
                </th>
                <th className="hand" onClick={sort('vlaknummer')}>
                  Vlaknummer <FontAwesomeIcon icon={getSortIconByFieldName('vlaknummer')} />
                </th>
                <th className="hand" onClick={sort('vullingnummer')}>
                  Vullingnummer <FontAwesomeIcon icon={getSortIconByFieldName('vullingnummer')} />
                </th>
                <th>
                  Heeft Spoor <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vullingList.map((vulling, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vulling/${vulling.id}`} color="link" size="sm">
                      {vulling.id}
                    </Button>
                  </td>
                  <td>{vulling.grondsoort}</td>
                  <td>{vulling.key}</td>
                  <td>{vulling.keyspoor}</td>
                  <td>{vulling.kleur}</td>
                  <td>{vulling.projectcd}</td>
                  <td>{vulling.putnummer}</td>
                  <td>{vulling.spoornummer}</td>
                  <td>{vulling.structuur}</td>
                  <td>{vulling.vlaknummer}</td>
                  <td>{vulling.vullingnummer}</td>
                  <td>{vulling.heeftSpoor ? <Link to={`/spoor/${vulling.heeftSpoor.id}`}>{vulling.heeftSpoor.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vulling/${vulling.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/vulling/${vulling.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vulling/${vulling.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vullings found</div>
        )}
      </div>
    </div>
  );
};

export default Vulling;
