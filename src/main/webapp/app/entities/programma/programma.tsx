import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './programma.reducer';

export const Programma = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const programmaList = useAppSelector(state => state.programma.entities);
  const loading = useAppSelector(state => state.programma.loading);

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
      <h2 id="programma-heading" data-cy="ProgrammaHeading">
        Programmas
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/programma/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Programma
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {programmaList && programmaList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th>
                  Heeft Kostenplaats <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Voor Programmasoort <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Voor Museumrelatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Hoortbij Raadsstuk <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {programmaList.map((programma, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/programma/${programma.id}`} color="link" size="sm">
                      {programma.id}
                    </Button>
                  </td>
                  <td>{programma.naam}</td>
                  <td>
                    {programma.heeftKostenplaats ? (
                      <Link to={`/kostenplaats/${programma.heeftKostenplaats.id}`}>{programma.heeftKostenplaats.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {programma.voorProgrammasoorts
                      ? programma.voorProgrammasoorts.map((val, j) => (
                          <span key={j}>
                            <Link to={`/programmasoort/${val.id}`}>{val.id}</Link>
                            {j === programma.voorProgrammasoorts.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {programma.voorMuseumrelatie ? (
                      <Link to={`/museumrelatie/${programma.voorMuseumrelatie.id}`}>{programma.voorMuseumrelatie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {programma.hoortbijRaadsstuks
                      ? programma.hoortbijRaadsstuks.map((val, j) => (
                          <span key={j}>
                            <Link to={`/raadsstuk/${val.id}`}>{val.id}</Link>
                            {j === programma.hoortbijRaadsstuks.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/programma/${programma.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/programma/${programma.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/programma/${programma.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Programmas found</div>
        )}
      </div>
    </div>
  );
};

export default Programma;
