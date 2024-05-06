import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './afwijkendbuitenlandscorrespondentieadresrol.reducer';

export const Afwijkendbuitenlandscorrespondentieadresrol = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const afwijkendbuitenlandscorrespondentieadresrolList = useAppSelector(
    state => state.afwijkendbuitenlandscorrespondentieadresrol.entities,
  );
  const loading = useAppSelector(state => state.afwijkendbuitenlandscorrespondentieadresrol.loading);

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
      <h2 id="afwijkendbuitenlandscorrespondentieadresrol-heading" data-cy="AfwijkendbuitenlandscorrespondentieadresrolHeading">
        Afwijkendbuitenlandscorrespondentieadresrols
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/afwijkendbuitenlandscorrespondentieadresrol/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Afwijkendbuitenlandscorrespondentieadresrol
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {afwijkendbuitenlandscorrespondentieadresrolList && afwijkendbuitenlandscorrespondentieadresrolList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adresbuitenland1')}>
                  Adresbuitenland 1 <FontAwesomeIcon icon={getSortIconByFieldName('adresbuitenland1')} />
                </th>
                <th className="hand" onClick={sort('adresbuitenland2')}>
                  Adresbuitenland 2 <FontAwesomeIcon icon={getSortIconByFieldName('adresbuitenland2')} />
                </th>
                <th className="hand" onClick={sort('adresbuitenland3')}>
                  Adresbuitenland 3 <FontAwesomeIcon icon={getSortIconByFieldName('adresbuitenland3')} />
                </th>
                <th className="hand" onClick={sort('landpostadres')}>
                  Landpostadres <FontAwesomeIcon icon={getSortIconByFieldName('landpostadres')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {afwijkendbuitenlandscorrespondentieadresrolList.map((afwijkendbuitenlandscorrespondentieadresrol, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/afwijkendbuitenlandscorrespondentieadresrol/${afwijkendbuitenlandscorrespondentieadresrol.id}`}
                      color="link"
                      size="sm"
                    >
                      {afwijkendbuitenlandscorrespondentieadresrol.id}
                    </Button>
                  </td>
                  <td>{afwijkendbuitenlandscorrespondentieadresrol.adresbuitenland1}</td>
                  <td>{afwijkendbuitenlandscorrespondentieadresrol.adresbuitenland2}</td>
                  <td>{afwijkendbuitenlandscorrespondentieadresrol.adresbuitenland3}</td>
                  <td>{afwijkendbuitenlandscorrespondentieadresrol.landpostadres}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/afwijkendbuitenlandscorrespondentieadresrol/${afwijkendbuitenlandscorrespondentieadresrol.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/afwijkendbuitenlandscorrespondentieadresrol/${afwijkendbuitenlandscorrespondentieadresrol.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/afwijkendbuitenlandscorrespondentieadresrol/${afwijkendbuitenlandscorrespondentieadresrol.id}/delete`)
                        }
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
          !loading && <div className="alert alert-warning">No Afwijkendbuitenlandscorrespondentieadresrols found</div>
        )}
      </div>
    </div>
  );
};

export default Afwijkendbuitenlandscorrespondentieadresrol;
