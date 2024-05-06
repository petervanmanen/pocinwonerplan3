import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './terreindeel.reducer';

export const Terreindeel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const terreindeelList = useAppSelector(state => state.terreindeel.entities);
  const loading = useAppSelector(state => state.terreindeel.loading);

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
      <h2 id="terreindeel-heading" data-cy="TerreindeelHeading">
        Terreindeels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/terreindeel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Terreindeel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {terreindeelList && terreindeelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('breedte')}>
                  Breedte <FontAwesomeIcon icon={getSortIconByFieldName('breedte')} />
                </th>
                <th className="hand" onClick={sort('cultuurhistorischwaardevol')}>
                  Cultuurhistorischwaardevol <FontAwesomeIcon icon={getSortIconByFieldName('cultuurhistorischwaardevol')} />
                </th>
                <th className="hand" onClick={sort('herplantplicht')}>
                  Herplantplicht <FontAwesomeIcon icon={getSortIconByFieldName('herplantplicht')} />
                </th>
                <th className="hand" onClick={sort('oppervlakte')}>
                  Oppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('oppervlakte')} />
                </th>
                <th className="hand" onClick={sort('optalud')}>
                  Optalud <FontAwesomeIcon icon={getSortIconByFieldName('optalud')} />
                </th>
                <th className="hand" onClick={sort('percentageloofbos')}>
                  Percentageloofbos <FontAwesomeIcon icon={getSortIconByFieldName('percentageloofbos')} />
                </th>
                <th className="hand" onClick={sort('terreindeelsoortnaam')}>
                  Terreindeelsoortnaam <FontAwesomeIcon icon={getSortIconByFieldName('terreindeelsoortnaam')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('typebewerking')}>
                  Typebewerking <FontAwesomeIcon icon={getSortIconByFieldName('typebewerking')} />
                </th>
                <th className="hand" onClick={sort('typeplus')}>
                  Typeplus <FontAwesomeIcon icon={getSortIconByFieldName('typeplus')} />
                </th>
                <th className="hand" onClick={sort('typeplus2')}>
                  Typeplus 2 <FontAwesomeIcon icon={getSortIconByFieldName('typeplus2')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {terreindeelList.map((terreindeel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/terreindeel/${terreindeel.id}`} color="link" size="sm">
                      {terreindeel.id}
                    </Button>
                  </td>
                  <td>{terreindeel.breedte}</td>
                  <td>{terreindeel.cultuurhistorischwaardevol}</td>
                  <td>{terreindeel.herplantplicht ? 'true' : 'false'}</td>
                  <td>{terreindeel.oppervlakte}</td>
                  <td>{terreindeel.optalud}</td>
                  <td>{terreindeel.percentageloofbos}</td>
                  <td>{terreindeel.terreindeelsoortnaam}</td>
                  <td>{terreindeel.type}</td>
                  <td>{terreindeel.typebewerking}</td>
                  <td>{terreindeel.typeplus}</td>
                  <td>{terreindeel.typeplus2}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/terreindeel/${terreindeel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/terreindeel/${terreindeel.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/terreindeel/${terreindeel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Terreindeels found</div>
        )}
      </div>
    </div>
  );
};

export default Terreindeel;
