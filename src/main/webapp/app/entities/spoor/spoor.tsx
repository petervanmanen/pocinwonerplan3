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

import { getEntities } from './spoor.reducer';

export const Spoor = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const spoorList = useAppSelector(state => state.spoor.entities);
  const loading = useAppSelector(state => state.spoor.loading);

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
      <h2 id="spoor-heading" data-cy="SpoorHeading">
        Spoors
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/spoor/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Spoor
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {spoorList && spoorList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aard')}>
                  Aard <FontAwesomeIcon icon={getSortIconByFieldName('aard')} />
                </th>
                <th className="hand" onClick={sort('beschrijving')}>
                  Beschrijving <FontAwesomeIcon icon={getSortIconByFieldName('beschrijving')} />
                </th>
                <th className="hand" onClick={sort('datering')}>
                  Datering <FontAwesomeIcon icon={getSortIconByFieldName('datering')} />
                </th>
                <th className="hand" onClick={sort('datum')}>
                  Datum <FontAwesomeIcon icon={getSortIconByFieldName('datum')} />
                </th>
                <th className="hand" onClick={sort('hoogteboven')}>
                  Hoogteboven <FontAwesomeIcon icon={getSortIconByFieldName('hoogteboven')} />
                </th>
                <th className="hand" onClick={sort('hoogteonder')}>
                  Hoogteonder <FontAwesomeIcon icon={getSortIconByFieldName('hoogteonder')} />
                </th>
                <th className="hand" onClick={sort('key')}>
                  Key <FontAwesomeIcon icon={getSortIconByFieldName('key')} />
                </th>
                <th className="hand" onClick={sort('keyvlak')}>
                  Keyvlak <FontAwesomeIcon icon={getSortIconByFieldName('keyvlak')} />
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
                <th className="hand" onClick={sort('vlaknummer')}>
                  Vlaknummer <FontAwesomeIcon icon={getSortIconByFieldName('vlaknummer')} />
                </th>
                <th className="hand" onClick={sort('vorm')}>
                  Vorm <FontAwesomeIcon icon={getSortIconByFieldName('vorm')} />
                </th>
                <th>
                  Heeft Vlak <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {spoorList.map((spoor, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/spoor/${spoor.id}`} color="link" size="sm">
                      {spoor.id}
                    </Button>
                  </td>
                  <td>{spoor.aard}</td>
                  <td>{spoor.beschrijving}</td>
                  <td>{spoor.datering}</td>
                  <td>{spoor.datum ? <TextFormat type="date" value={spoor.datum} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{spoor.hoogteboven}</td>
                  <td>{spoor.hoogteonder}</td>
                  <td>{spoor.key}</td>
                  <td>{spoor.keyvlak}</td>
                  <td>{spoor.projectcd}</td>
                  <td>{spoor.putnummer}</td>
                  <td>{spoor.spoornummer}</td>
                  <td>{spoor.vlaknummer}</td>
                  <td>{spoor.vorm}</td>
                  <td>{spoor.heeftVlak ? <Link to={`/vlak/${spoor.heeftVlak.id}`}>{spoor.heeftVlak.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/spoor/${spoor.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/spoor/${spoor.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/spoor/${spoor.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Spoors found</div>
        )}
      </div>
    </div>
  );
};

export default Spoor;
