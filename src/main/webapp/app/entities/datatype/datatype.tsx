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

import { getEntities } from './datatype.reducer';

export const Datatype = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const datatypeList = useAppSelector(state => state.datatype.entities);
  const loading = useAppSelector(state => state.datatype.loading);

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
      <h2 id="datatype-heading" data-cy="DatatypeHeading">
        Datatypes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/datatype/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Datatype
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {datatypeList && datatypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('datumopname')}>
                  Datumopname <FontAwesomeIcon icon={getSortIconByFieldName('datumopname')} />
                </th>
                <th className="hand" onClick={sort('definitie')}>
                  Definitie <FontAwesomeIcon icon={getSortIconByFieldName('definitie')} />
                </th>
                <th className="hand" onClick={sort('domein')}>
                  Domein <FontAwesomeIcon icon={getSortIconByFieldName('domein')} />
                </th>
                <th className="hand" onClick={sort('eaguid')}>
                  Eaguid <FontAwesomeIcon icon={getSortIconByFieldName('eaguid')} />
                </th>
                <th className="hand" onClick={sort('herkomst')}>
                  Herkomst <FontAwesomeIcon icon={getSortIconByFieldName('herkomst')} />
                </th>
                <th className="hand" onClick={sort('id')}>
                  Id <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('kardinaliteit')}>
                  Kardinaliteit <FontAwesomeIcon icon={getSortIconByFieldName('kardinaliteit')} />
                </th>
                <th className="hand" onClick={sort('lengte')}>
                  Lengte <FontAwesomeIcon icon={getSortIconByFieldName('lengte')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('patroon')}>
                  Patroon <FontAwesomeIcon icon={getSortIconByFieldName('patroon')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {datatypeList.map((datatype, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/datatype/${datatype.id}`} color="link" size="sm">
                      {datatype.id}
                    </Button>
                  </td>
                  <td>
                    {datatype.datumopname ? <TextFormat type="date" value={datatype.datumopname} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{datatype.definitie}</td>
                  <td>{datatype.domein}</td>
                  <td>{datatype.eaguid}</td>
                  <td>{datatype.herkomst}</td>
                  <td>{datatype.kardinaliteit}</td>
                  <td>{datatype.lengte}</td>
                  <td>{datatype.naam}</td>
                  <td>{datatype.patroon}</td>
                  <td>{datatype.toelichting}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/datatype/${datatype.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/datatype/${datatype.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/datatype/${datatype.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Datatypes found</div>
        )}
      </div>
    </div>
  );
};

export default Datatype;
