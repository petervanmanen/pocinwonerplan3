import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './brug.reducer';

export const Brug = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const brugList = useAppSelector(state => state.brug.entities);
  const loading = useAppSelector(state => state.brug.loading);

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
      <h2 id="brug-heading" data-cy="BrugHeading">
        Brugs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/brug/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Brug
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {brugList && brugList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantaloverspanningen')}>
                  Aantaloverspanningen <FontAwesomeIcon icon={getSortIconByFieldName('aantaloverspanningen')} />
                </th>
                <th className="hand" onClick={sort('bedienaar')}>
                  Bedienaar <FontAwesomeIcon icon={getSortIconByFieldName('bedienaar')} />
                </th>
                <th className="hand" onClick={sort('bedieningstijden')}>
                  Bedieningstijden <FontAwesomeIcon icon={getSortIconByFieldName('bedieningstijden')} />
                </th>
                <th className="hand" onClick={sort('belastingklassenieuw')}>
                  Belastingklassenieuw <FontAwesomeIcon icon={getSortIconByFieldName('belastingklassenieuw')} />
                </th>
                <th className="hand" onClick={sort('belastingklasseoud')}>
                  Belastingklasseoud <FontAwesomeIcon icon={getSortIconByFieldName('belastingklasseoud')} />
                </th>
                <th className="hand" onClick={sort('beweegbaar')}>
                  Beweegbaar <FontAwesomeIcon icon={getSortIconByFieldName('beweegbaar')} />
                </th>
                <th className="hand" onClick={sort('doorrijbreedte')}>
                  Doorrijbreedte <FontAwesomeIcon icon={getSortIconByFieldName('doorrijbreedte')} />
                </th>
                <th className="hand" onClick={sort('draagvermogen')}>
                  Draagvermogen <FontAwesomeIcon icon={getSortIconByFieldName('draagvermogen')} />
                </th>
                <th className="hand" onClick={sort('hoofdroute')}>
                  Hoofdroute <FontAwesomeIcon icon={getSortIconByFieldName('hoofdroute')} />
                </th>
                <th className="hand" onClick={sort('hoofdvaarroute')}>
                  Hoofdvaarroute <FontAwesomeIcon icon={getSortIconByFieldName('hoofdvaarroute')} />
                </th>
                <th className="hand" onClick={sort('maximaaltoelaatbaarvoertuiggewicht')}>
                  Maximaaltoelaatbaarvoertuiggewicht <FontAwesomeIcon icon={getSortIconByFieldName('maximaaltoelaatbaarvoertuiggewicht')} />
                </th>
                <th className="hand" onClick={sort('maximaleasbelasting')}>
                  Maximaleasbelasting <FontAwesomeIcon icon={getSortIconByFieldName('maximaleasbelasting')} />
                </th>
                <th className="hand" onClick={sort('maximaleoverspanning')}>
                  Maximaleoverspanning <FontAwesomeIcon icon={getSortIconByFieldName('maximaleoverspanning')} />
                </th>
                <th className="hand" onClick={sort('statischmoment')}>
                  Statischmoment <FontAwesomeIcon icon={getSortIconByFieldName('statischmoment')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('typeplus')}>
                  Typeplus <FontAwesomeIcon icon={getSortIconByFieldName('typeplus')} />
                </th>
                <th className="hand" onClick={sort('zwaarstevoertuig')}>
                  Zwaarstevoertuig <FontAwesomeIcon icon={getSortIconByFieldName('zwaarstevoertuig')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {brugList.map((brug, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/brug/${brug.id}`} color="link" size="sm">
                      {brug.id}
                    </Button>
                  </td>
                  <td>{brug.aantaloverspanningen}</td>
                  <td>{brug.bedienaar}</td>
                  <td>{brug.bedieningstijden}</td>
                  <td>{brug.belastingklassenieuw}</td>
                  <td>{brug.belastingklasseoud}</td>
                  <td>{brug.beweegbaar ? 'true' : 'false'}</td>
                  <td>{brug.doorrijbreedte}</td>
                  <td>{brug.draagvermogen}</td>
                  <td>{brug.hoofdroute}</td>
                  <td>{brug.hoofdvaarroute}</td>
                  <td>{brug.maximaaltoelaatbaarvoertuiggewicht}</td>
                  <td>{brug.maximaleasbelasting}</td>
                  <td>{brug.maximaleoverspanning}</td>
                  <td>{brug.statischmoment}</td>
                  <td>{brug.type}</td>
                  <td>{brug.typeplus}</td>
                  <td>{brug.zwaarstevoertuig}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/brug/${brug.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/brug/${brug.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/brug/${brug.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Brugs found</div>
        )}
      </div>
    </div>
  );
};

export default Brug;
