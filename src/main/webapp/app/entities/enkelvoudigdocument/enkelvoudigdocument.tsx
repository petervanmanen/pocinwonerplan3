import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './enkelvoudigdocument.reducer';

export const Enkelvoudigdocument = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const enkelvoudigdocumentList = useAppSelector(state => state.enkelvoudigdocument.entities);
  const loading = useAppSelector(state => state.enkelvoudigdocument.loading);

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
      <h2 id="enkelvoudigdocument-heading" data-cy="EnkelvoudigdocumentHeading">
        Enkelvoudigdocuments
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/enkelvoudigdocument/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Enkelvoudigdocument
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {enkelvoudigdocumentList && enkelvoudigdocumentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('bestandsnaam')}>
                  Bestandsnaam <FontAwesomeIcon icon={getSortIconByFieldName('bestandsnaam')} />
                </th>
                <th className="hand" onClick={sort('documentformaat')}>
                  Documentformaat <FontAwesomeIcon icon={getSortIconByFieldName('documentformaat')} />
                </th>
                <th className="hand" onClick={sort('documentinhoud')}>
                  Documentinhoud <FontAwesomeIcon icon={getSortIconByFieldName('documentinhoud')} />
                </th>
                <th className="hand" onClick={sort('documentlink')}>
                  Documentlink <FontAwesomeIcon icon={getSortIconByFieldName('documentlink')} />
                </th>
                <th className="hand" onClick={sort('documentstatus')}>
                  Documentstatus <FontAwesomeIcon icon={getSortIconByFieldName('documentstatus')} />
                </th>
                <th className="hand" onClick={sort('documenttaal')}>
                  Documenttaal <FontAwesomeIcon icon={getSortIconByFieldName('documenttaal')} />
                </th>
                <th className="hand" onClick={sort('documentversie')}>
                  Documentversie <FontAwesomeIcon icon={getSortIconByFieldName('documentversie')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {enkelvoudigdocumentList.map((enkelvoudigdocument, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/enkelvoudigdocument/${enkelvoudigdocument.id}`} color="link" size="sm">
                      {enkelvoudigdocument.id}
                    </Button>
                  </td>
                  <td>{enkelvoudigdocument.bestandsnaam}</td>
                  <td>{enkelvoudigdocument.documentformaat}</td>
                  <td>{enkelvoudigdocument.documentinhoud}</td>
                  <td>{enkelvoudigdocument.documentlink}</td>
                  <td>{enkelvoudigdocument.documentstatus}</td>
                  <td>{enkelvoudigdocument.documenttaal}</td>
                  <td>{enkelvoudigdocument.documentversie}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/enkelvoudigdocument/${enkelvoudigdocument.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/enkelvoudigdocument/${enkelvoudigdocument.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/enkelvoudigdocument/${enkelvoudigdocument.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Enkelvoudigdocuments found</div>
        )}
      </div>
    </div>
  );
};

export default Enkelvoudigdocument;
