import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './waterobject.reducer';

export const Waterobject = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const waterobjectList = useAppSelector(state => state.waterobject.entities);
  const loading = useAppSelector(state => state.waterobject.loading);

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
      <h2 id="waterobject-heading" data-cy="WaterobjectHeading">
        Waterobjects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/waterobject/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Waterobject
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {waterobjectList && waterobjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('breedte')}>
                  Breedte <FontAwesomeIcon icon={getSortIconByFieldName('breedte')} />
                </th>
                <th className="hand" onClick={sort('folie')}>
                  Folie <FontAwesomeIcon icon={getSortIconByFieldName('folie')} />
                </th>
                <th className="hand" onClick={sort('hoogte')}>
                  Hoogte <FontAwesomeIcon icon={getSortIconByFieldName('hoogte')} />
                </th>
                <th className="hand" onClick={sort('infiltrerendoppervlak')}>
                  Infiltrerendoppervlak <FontAwesomeIcon icon={getSortIconByFieldName('infiltrerendoppervlak')} />
                </th>
                <th className="hand" onClick={sort('infiltrerendvermogen')}>
                  Infiltrerendvermogen <FontAwesomeIcon icon={getSortIconByFieldName('infiltrerendvermogen')} />
                </th>
                <th className="hand" onClick={sort('lengte')}>
                  Lengte <FontAwesomeIcon icon={getSortIconByFieldName('lengte')} />
                </th>
                <th className="hand" onClick={sort('lozingspunt')}>
                  Lozingspunt <FontAwesomeIcon icon={getSortIconByFieldName('lozingspunt')} />
                </th>
                <th className="hand" onClick={sort('oppervlakte')}>
                  Oppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('oppervlakte')} />
                </th>
                <th className="hand" onClick={sort('porositeit')}>
                  Porositeit <FontAwesomeIcon icon={getSortIconByFieldName('porositeit')} />
                </th>
                <th className="hand" onClick={sort('streefdiepte')}>
                  Streefdiepte <FontAwesomeIcon icon={getSortIconByFieldName('streefdiepte')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('typeplus')}>
                  Typeplus <FontAwesomeIcon icon={getSortIconByFieldName('typeplus')} />
                </th>
                <th className="hand" onClick={sort('typeplus2')}>
                  Typeplus 2 <FontAwesomeIcon icon={getSortIconByFieldName('typeplus2')} />
                </th>
                <th className="hand" onClick={sort('typevaarwater')}>
                  Typevaarwater <FontAwesomeIcon icon={getSortIconByFieldName('typevaarwater')} />
                </th>
                <th className="hand" onClick={sort('typewaterplant')}>
                  Typewaterplant <FontAwesomeIcon icon={getSortIconByFieldName('typewaterplant')} />
                </th>
                <th className="hand" onClick={sort('uitstroomniveau')}>
                  Uitstroomniveau <FontAwesomeIcon icon={getSortIconByFieldName('uitstroomniveau')} />
                </th>
                <th className="hand" onClick={sort('vaarwegtraject')}>
                  Vaarwegtraject <FontAwesomeIcon icon={getSortIconByFieldName('vaarwegtraject')} />
                </th>
                <th className="hand" onClick={sort('vorm')}>
                  Vorm <FontAwesomeIcon icon={getSortIconByFieldName('vorm')} />
                </th>
                <th className="hand" onClick={sort('waternaam')}>
                  Waternaam <FontAwesomeIcon icon={getSortIconByFieldName('waternaam')} />
                </th>
                <th className="hand" onClick={sort('waterpeil')}>
                  Waterpeil <FontAwesomeIcon icon={getSortIconByFieldName('waterpeil')} />
                </th>
                <th className="hand" onClick={sort('waterpeilwinter')}>
                  Waterpeilwinter <FontAwesomeIcon icon={getSortIconByFieldName('waterpeilwinter')} />
                </th>
                <th className="hand" onClick={sort('waterpeilzomer')}>
                  Waterpeilzomer <FontAwesomeIcon icon={getSortIconByFieldName('waterpeilzomer')} />
                </th>
                <th className="hand" onClick={sort('waterplanten')}>
                  Waterplanten <FontAwesomeIcon icon={getSortIconByFieldName('waterplanten')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {waterobjectList.map((waterobject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/waterobject/${waterobject.id}`} color="link" size="sm">
                      {waterobject.id}
                    </Button>
                  </td>
                  <td>{waterobject.breedte}</td>
                  <td>{waterobject.folie ? 'true' : 'false'}</td>
                  <td>{waterobject.hoogte}</td>
                  <td>{waterobject.infiltrerendoppervlak}</td>
                  <td>{waterobject.infiltrerendvermogen}</td>
                  <td>{waterobject.lengte}</td>
                  <td>{waterobject.lozingspunt}</td>
                  <td>{waterobject.oppervlakte}</td>
                  <td>{waterobject.porositeit}</td>
                  <td>{waterobject.streefdiepte}</td>
                  <td>{waterobject.type}</td>
                  <td>{waterobject.typeplus}</td>
                  <td>{waterobject.typeplus2}</td>
                  <td>{waterobject.typevaarwater}</td>
                  <td>{waterobject.typewaterplant}</td>
                  <td>{waterobject.uitstroomniveau}</td>
                  <td>{waterobject.vaarwegtraject}</td>
                  <td>{waterobject.vorm}</td>
                  <td>{waterobject.waternaam}</td>
                  <td>{waterobject.waterpeil}</td>
                  <td>{waterobject.waterpeilwinter}</td>
                  <td>{waterobject.waterpeilzomer}</td>
                  <td>{waterobject.waterplanten ? 'true' : 'false'}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/waterobject/${waterobject.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/waterobject/${waterobject.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/waterobject/${waterobject.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Waterobjects found</div>
        )}
      </div>
    </div>
  );
};

export default Waterobject;
