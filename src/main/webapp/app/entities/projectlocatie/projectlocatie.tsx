import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './projectlocatie.reducer';

export const Projectlocatie = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const projectlocatieList = useAppSelector(state => state.projectlocatie.entities);
  const loading = useAppSelector(state => state.projectlocatie.loading);

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
      <h2 id="projectlocatie-heading" data-cy="ProjectlocatieHeading">
        Projectlocaties
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/projectlocatie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Projectlocatie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {projectlocatieList && projectlocatieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adres')}>
                  Adres <FontAwesomeIcon icon={getSortIconByFieldName('adres')} />
                </th>
                <th className="hand" onClick={sort('kadastraalperceel')}>
                  Kadastraalperceel <FontAwesomeIcon icon={getSortIconByFieldName('kadastraalperceel')} />
                </th>
                <th className="hand" onClick={sort('kadastralegemeente')}>
                  Kadastralegemeente <FontAwesomeIcon icon={getSortIconByFieldName('kadastralegemeente')} />
                </th>
                <th className="hand" onClick={sort('kadastralesectie')}>
                  Kadastralesectie <FontAwesomeIcon icon={getSortIconByFieldName('kadastralesectie')} />
                </th>
                <th>
                  Betreft Locatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Project <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Verzoek <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {projectlocatieList.map((projectlocatie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/projectlocatie/${projectlocatie.id}`} color="link" size="sm">
                      {projectlocatie.id}
                    </Button>
                  </td>
                  <td>{projectlocatie.adres}</td>
                  <td>{projectlocatie.kadastraalperceel}</td>
                  <td>{projectlocatie.kadastralegemeente}</td>
                  <td>{projectlocatie.kadastralesectie}</td>
                  <td>
                    {projectlocatie.betreftLocatie ? (
                      <Link to={`/locatie/${projectlocatie.betreftLocatie.id}`}>{projectlocatie.betreftLocatie.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {projectlocatie.heeftProject ? (
                      <Link to={`/project/${projectlocatie.heeftProject.id}`}>{projectlocatie.heeftProject.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {projectlocatie.betreftVerzoeks
                      ? projectlocatie.betreftVerzoeks.map((val, j) => (
                          <span key={j}>
                            <Link to={`/verzoek/${val.id}`}>{val.id}</Link>
                            {j === projectlocatie.betreftVerzoeks.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/projectlocatie/${projectlocatie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/projectlocatie/${projectlocatie.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/projectlocatie/${projectlocatie.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Projectlocaties found</div>
        )}
      </div>
    </div>
  );
};

export default Projectlocatie;
