import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './viaduct.reducer';

export const Viaduct = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const viaductList = useAppSelector(state => state.viaduct.entities);
  const loading = useAppSelector(state => state.viaduct.loading);

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
      <h2 id="viaduct-heading" data-cy="ViaductHeading">
        Viaducts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/viaduct/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Viaduct
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {viaductList && viaductList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantaloverspanningen')}>
                  Aantaloverspanningen <FontAwesomeIcon icon={getSortIconByFieldName('aantaloverspanningen')} />
                </th>
                <th className="hand" onClick={sort('belastingklassenieuw')}>
                  Belastingklassenieuw <FontAwesomeIcon icon={getSortIconByFieldName('belastingklassenieuw')} />
                </th>
                <th className="hand" onClick={sort('belastingklasseoud')}>
                  Belastingklasseoud <FontAwesomeIcon icon={getSortIconByFieldName('belastingklasseoud')} />
                </th>
                <th className="hand" onClick={sort('draagvermogen')}>
                  Draagvermogen <FontAwesomeIcon icon={getSortIconByFieldName('draagvermogen')} />
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
                <th className="hand" onClick={sort('overbruggingsobjectdoorrijopening')}>
                  Overbruggingsobjectdoorrijopening <FontAwesomeIcon icon={getSortIconByFieldName('overbruggingsobjectdoorrijopening')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('waterobject')}>
                  Waterobject <FontAwesomeIcon icon={getSortIconByFieldName('waterobject')} />
                </th>
                <th className="hand" onClick={sort('zwaarstevoertuig')}>
                  Zwaarstevoertuig <FontAwesomeIcon icon={getSortIconByFieldName('zwaarstevoertuig')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {viaductList.map((viaduct, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/viaduct/${viaduct.id}`} color="link" size="sm">
                      {viaduct.id}
                    </Button>
                  </td>
                  <td>{viaduct.aantaloverspanningen}</td>
                  <td>{viaduct.belastingklassenieuw}</td>
                  <td>{viaduct.belastingklasseoud}</td>
                  <td>{viaduct.draagvermogen}</td>
                  <td>{viaduct.maximaaltoelaatbaarvoertuiggewicht}</td>
                  <td>{viaduct.maximaleasbelasting}</td>
                  <td>{viaduct.maximaleoverspanning}</td>
                  <td>{viaduct.overbruggingsobjectdoorrijopening}</td>
                  <td>{viaduct.type}</td>
                  <td>{viaduct.waterobject}</td>
                  <td>{viaduct.zwaarstevoertuig}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/viaduct/${viaduct.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/viaduct/${viaduct.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/viaduct/${viaduct.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Viaducts found</div>
        )}
      </div>
    </div>
  );
};

export default Viaduct;
