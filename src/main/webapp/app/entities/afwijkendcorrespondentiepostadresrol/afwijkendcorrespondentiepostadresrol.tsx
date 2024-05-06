import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './afwijkendcorrespondentiepostadresrol.reducer';

export const Afwijkendcorrespondentiepostadresrol = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const afwijkendcorrespondentiepostadresrolList = useAppSelector(state => state.afwijkendcorrespondentiepostadresrol.entities);
  const loading = useAppSelector(state => state.afwijkendcorrespondentiepostadresrol.loading);

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
      <h2 id="afwijkendcorrespondentiepostadresrol-heading" data-cy="AfwijkendcorrespondentiepostadresrolHeading">
        Afwijkendcorrespondentiepostadresrols
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/afwijkendcorrespondentiepostadresrol/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Afwijkendcorrespondentiepostadresrol
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {afwijkendcorrespondentiepostadresrolList && afwijkendcorrespondentiepostadresrolList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('postadrestype')}>
                  Postadrestype <FontAwesomeIcon icon={getSortIconByFieldName('postadrestype')} />
                </th>
                <th className="hand" onClick={sort('postbusofantwoordnummer')}>
                  Postbusofantwoordnummer <FontAwesomeIcon icon={getSortIconByFieldName('postbusofantwoordnummer')} />
                </th>
                <th className="hand" onClick={sort('postcodepostadres')}>
                  Postcodepostadres <FontAwesomeIcon icon={getSortIconByFieldName('postcodepostadres')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {afwijkendcorrespondentiepostadresrolList.map((afwijkendcorrespondentiepostadresrol, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/afwijkendcorrespondentiepostadresrol/${afwijkendcorrespondentiepostadresrol.id}`}
                      color="link"
                      size="sm"
                    >
                      {afwijkendcorrespondentiepostadresrol.id}
                    </Button>
                  </td>
                  <td>{afwijkendcorrespondentiepostadresrol.postadrestype}</td>
                  <td>{afwijkendcorrespondentiepostadresrol.postbusofantwoordnummer}</td>
                  <td>{afwijkendcorrespondentiepostadresrol.postcodepostadres}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/afwijkendcorrespondentiepostadresrol/${afwijkendcorrespondentiepostadresrol.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/afwijkendcorrespondentiepostadresrol/${afwijkendcorrespondentiepostadresrol.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/afwijkendcorrespondentiepostadresrol/${afwijkendcorrespondentiepostadresrol.id}/delete`)
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
          !loading && <div className="alert alert-warning">No Afwijkendcorrespondentiepostadresrols found</div>
        )}
      </div>
    </div>
  );
};

export default Afwijkendcorrespondentiepostadresrol;
