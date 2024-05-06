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

import { getEntities } from './project.reducer';

export const Project = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const projectList = useAppSelector(state => state.project.entities);
  const loading = useAppSelector(state => state.project.loading);

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
      <h2 id="project-heading" data-cy="ProjectHeading">
        Projects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/project/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Project
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {projectList && projectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('coordinaten')}>
                  Coordinaten <FontAwesomeIcon icon={getSortIconByFieldName('coordinaten')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('jaartot')}>
                  Jaartot <FontAwesomeIcon icon={getSortIconByFieldName('jaartot')} />
                </th>
                <th className="hand" onClick={sort('jaarvan')}>
                  Jaarvan <FontAwesomeIcon icon={getSortIconByFieldName('jaarvan')} />
                </th>
                <th className="hand" onClick={sort('locatie')}>
                  Locatie <FontAwesomeIcon icon={getSortIconByFieldName('locatie')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('naamcode')}>
                  Naamcode <FontAwesomeIcon icon={getSortIconByFieldName('naamcode')} />
                </th>
                <th className="hand" onClick={sort('projectcd')}>
                  Projectcd <FontAwesomeIcon icon={getSortIconByFieldName('projectcd')} />
                </th>
                <th className="hand" onClick={sort('toponiem')}>
                  Toponiem <FontAwesomeIcon icon={getSortIconByFieldName('toponiem')} />
                </th>
                <th className="hand" onClick={sort('trefwoorden')}>
                  Trefwoorden <FontAwesomeIcon icon={getSortIconByFieldName('trefwoorden')} />
                </th>
                <th>
                  Heeftuitstroomreden Uitstroomreden <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftresultaat Resultaat <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Soortproject Projectsoort <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Wordtbegrensddoor Locatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Kostenplaats <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftproject Traject <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {projectList.map((project, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/project/${project.id}`} color="link" size="sm">
                      {project.id}
                    </Button>
                  </td>
                  <td>{project.coordinaten}</td>
                  <td>
                    {project.datumeinde ? <TextFormat type="date" value={project.datumeinde} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {project.datumstart ? <TextFormat type="date" value={project.datumstart} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{project.jaartot}</td>
                  <td>{project.jaarvan}</td>
                  <td>{project.locatie}</td>
                  <td>{project.naam}</td>
                  <td>{project.naamcode}</td>
                  <td>{project.projectcd}</td>
                  <td>{project.toponiem}</td>
                  <td>{project.trefwoorden}</td>
                  <td>
                    {project.heeftuitstroomredenUitstroomreden ? (
                      <Link to={`/uitstroomreden/${project.heeftuitstroomredenUitstroomreden.id}`}>
                        {project.heeftuitstroomredenUitstroomreden.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {project.heeftresultaatResultaat ? (
                      <Link to={`/resultaat/${project.heeftresultaatResultaat.id}`}>{project.heeftresultaatResultaat.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {project.soortprojectProjectsoort ? (
                      <Link to={`/projectsoort/${project.soortprojectProjectsoort.id}`}>{project.soortprojectProjectsoort.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {project.wordtbegrensddoorLocaties
                      ? project.wordtbegrensddoorLocaties.map((val, j) => (
                          <span key={j}>
                            <Link to={`/locatie/${val.id}`}>{val.id}</Link>
                            {j === project.wordtbegrensddoorLocaties.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {project.heeftKostenplaats
                      ? project.heeftKostenplaats.map((val, j) => (
                          <span key={j}>
                            <Link to={`/kostenplaats/${val.id}`}>{val.id}</Link>
                            {j === project.heeftKostenplaats.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {project.heeftprojectTraject ? (
                      <Link to={`/traject/${project.heeftprojectTraject.id}`}>{project.heeftprojectTraject.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/project/${project.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/project/${project.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/project/${project.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Projects found</div>
        )}
      </div>
    </div>
  );
};

export default Project;
