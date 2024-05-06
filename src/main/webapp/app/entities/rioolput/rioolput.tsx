import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './rioolput.reducer';

export const Rioolput = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const rioolputList = useAppSelector(state => state.rioolput.entities);
  const loading = useAppSelector(state => state.rioolput.loading);

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
      <h2 id="rioolput-heading" data-cy="RioolputHeading">
        Rioolputs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/rioolput/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Rioolput
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {rioolputList && rioolputList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantalbedrijven')}>
                  Aantalbedrijven <FontAwesomeIcon icon={getSortIconByFieldName('aantalbedrijven')} />
                </th>
                <th className="hand" onClick={sort('aantalrecreatie')}>
                  Aantalrecreatie <FontAwesomeIcon icon={getSortIconByFieldName('aantalrecreatie')} />
                </th>
                <th className="hand" onClick={sort('aantalwoningen')}>
                  Aantalwoningen <FontAwesomeIcon icon={getSortIconByFieldName('aantalwoningen')} />
                </th>
                <th className="hand" onClick={sort('afvoerendoppervlak')}>
                  Afvoerendoppervlak <FontAwesomeIcon icon={getSortIconByFieldName('afvoerendoppervlak')} />
                </th>
                <th className="hand" onClick={sort('bergendoppervlak')}>
                  Bergendoppervlak <FontAwesomeIcon icon={getSortIconByFieldName('bergendoppervlak')} />
                </th>
                <th className="hand" onClick={sort('rioolputconstructieonderdeel')}>
                  Rioolputconstructieonderdeel <FontAwesomeIcon icon={getSortIconByFieldName('rioolputconstructieonderdeel')} />
                </th>
                <th className="hand" onClick={sort('rioolputrioolleiding')}>
                  Rioolputrioolleiding <FontAwesomeIcon icon={getSortIconByFieldName('rioolputrioolleiding')} />
                </th>
                <th className="hand" onClick={sort('risicogebied')}>
                  Risicogebied <FontAwesomeIcon icon={getSortIconByFieldName('risicogebied')} />
                </th>
                <th className="hand" onClick={sort('toegangbreedte')}>
                  Toegangbreedte <FontAwesomeIcon icon={getSortIconByFieldName('toegangbreedte')} />
                </th>
                <th className="hand" onClick={sort('toeganglengte')}>
                  Toeganglengte <FontAwesomeIcon icon={getSortIconByFieldName('toeganglengte')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('typeplus')}>
                  Typeplus <FontAwesomeIcon icon={getSortIconByFieldName('typeplus')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {rioolputList.map((rioolput, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/rioolput/${rioolput.id}`} color="link" size="sm">
                      {rioolput.id}
                    </Button>
                  </td>
                  <td>{rioolput.aantalbedrijven}</td>
                  <td>{rioolput.aantalrecreatie}</td>
                  <td>{rioolput.aantalwoningen}</td>
                  <td>{rioolput.afvoerendoppervlak}</td>
                  <td>{rioolput.bergendoppervlak}</td>
                  <td>{rioolput.rioolputconstructieonderdeel}</td>
                  <td>{rioolput.rioolputrioolleiding}</td>
                  <td>{rioolput.risicogebied}</td>
                  <td>{rioolput.toegangbreedte}</td>
                  <td>{rioolput.toeganglengte}</td>
                  <td>{rioolput.type}</td>
                  <td>{rioolput.typeplus}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/rioolput/${rioolput.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/rioolput/${rioolput.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/rioolput/${rioolput.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Rioolputs found</div>
        )}
      </div>
    </div>
  );
};

export default Rioolput;
