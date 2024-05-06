import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './handelsnamenmaatschappelijkeactiviteit.reducer';

export const Handelsnamenmaatschappelijkeactiviteit = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const handelsnamenmaatschappelijkeactiviteitList = useAppSelector(state => state.handelsnamenmaatschappelijkeactiviteit.entities);
  const loading = useAppSelector(state => state.handelsnamenmaatschappelijkeactiviteit.loading);

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
      <h2 id="handelsnamenmaatschappelijkeactiviteit-heading" data-cy="HandelsnamenmaatschappelijkeactiviteitHeading">
        Handelsnamenmaatschappelijkeactiviteits
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/handelsnamenmaatschappelijkeactiviteit/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Handelsnamenmaatschappelijkeactiviteit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {handelsnamenmaatschappelijkeactiviteitList && handelsnamenmaatschappelijkeactiviteitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('handelsnaam')}>
                  Handelsnaam <FontAwesomeIcon icon={getSortIconByFieldName('handelsnaam')} />
                </th>
                <th className="hand" onClick={sort('verkortenaam')}>
                  Verkortenaam <FontAwesomeIcon icon={getSortIconByFieldName('verkortenaam')} />
                </th>
                <th className="hand" onClick={sort('volgorde')}>
                  Volgorde <FontAwesomeIcon icon={getSortIconByFieldName('volgorde')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {handelsnamenmaatschappelijkeactiviteitList.map((handelsnamenmaatschappelijkeactiviteit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/handelsnamenmaatschappelijkeactiviteit/${handelsnamenmaatschappelijkeactiviteit.id}`}
                      color="link"
                      size="sm"
                    >
                      {handelsnamenmaatschappelijkeactiviteit.id}
                    </Button>
                  </td>
                  <td>{handelsnamenmaatschappelijkeactiviteit.handelsnaam}</td>
                  <td>{handelsnamenmaatschappelijkeactiviteit.verkortenaam}</td>
                  <td>{handelsnamenmaatschappelijkeactiviteit.volgorde}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/handelsnamenmaatschappelijkeactiviteit/${handelsnamenmaatschappelijkeactiviteit.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/handelsnamenmaatschappelijkeactiviteit/${handelsnamenmaatschappelijkeactiviteit.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/handelsnamenmaatschappelijkeactiviteit/${handelsnamenmaatschappelijkeactiviteit.id}/delete`)
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
          !loading && <div className="alert alert-warning">No Handelsnamenmaatschappelijkeactiviteits found</div>
        )}
      </div>
    </div>
  );
};

export default Handelsnamenmaatschappelijkeactiviteit;
