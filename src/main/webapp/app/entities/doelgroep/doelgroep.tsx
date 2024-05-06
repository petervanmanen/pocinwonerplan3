import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './doelgroep.reducer';

export const Doelgroep = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const doelgroepList = useAppSelector(state => state.doelgroep.entities);
  const loading = useAppSelector(state => state.doelgroep.loading);

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
      <h2 id="doelgroep-heading" data-cy="DoelgroepHeading">
        Doelgroeps
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/doelgroep/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Doelgroep
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {doelgroepList && doelgroepList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('branch')}>
                  Branch <FontAwesomeIcon icon={getSortIconByFieldName('branch')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('segment')}>
                  Segment <FontAwesomeIcon icon={getSortIconByFieldName('segment')} />
                </th>
                <th>
                  Bestaatuit Doelgroep 2 <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Valtbinnen Museumrelatie <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {doelgroepList.map((doelgroep, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/doelgroep/${doelgroep.id}`} color="link" size="sm">
                      {doelgroep.id}
                    </Button>
                  </td>
                  <td>{doelgroep.branch}</td>
                  <td>{doelgroep.naam}</td>
                  <td>{doelgroep.omschrijving}</td>
                  <td>{doelgroep.segment}</td>
                  <td>
                    {doelgroep.bestaatuitDoelgroep2 ? (
                      <Link to={`/doelgroep/${doelgroep.bestaatuitDoelgroep2.id}`}>{doelgroep.bestaatuitDoelgroep2.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {doelgroep.valtbinnenMuseumrelaties
                      ? doelgroep.valtbinnenMuseumrelaties.map((val, j) => (
                          <span key={j}>
                            <Link to={`/museumrelatie/${val.id}`}>{val.id}</Link>
                            {j === doelgroep.valtbinnenMuseumrelaties.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/doelgroep/${doelgroep.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/doelgroep/${doelgroep.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/doelgroep/${doelgroep.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Doelgroeps found</div>
        )}
      </div>
    </div>
  );
};

export default Doelgroep;
