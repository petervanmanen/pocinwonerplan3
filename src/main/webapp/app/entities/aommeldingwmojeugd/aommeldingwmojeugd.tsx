import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './aommeldingwmojeugd.reducer';

export const Aommeldingwmojeugd = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const aommeldingwmojeugdList = useAppSelector(state => state.aommeldingwmojeugd.entities);
  const loading = useAppSelector(state => state.aommeldingwmojeugd.loading);

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
      <h2 id="aommeldingwmojeugd-heading" data-cy="AommeldingwmojeugdHeading">
        Aommeldingwmojeugds
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/aommeldingwmojeugd/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Aommeldingwmojeugd
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {aommeldingwmojeugdList && aommeldingwmojeugdList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanmelder')}>
                  Aanmelder <FontAwesomeIcon icon={getSortIconByFieldName('aanmelder')} />
                </th>
                <th className="hand" onClick={sort('aanmeldingdoor')}>
                  Aanmeldingdoor <FontAwesomeIcon icon={getSortIconByFieldName('aanmeldingdoor')} />
                </th>
                <th className="hand" onClick={sort('aanmeldingdoorlandelijk')}>
                  Aanmeldingdoorlandelijk <FontAwesomeIcon icon={getSortIconByFieldName('aanmeldingdoorlandelijk')} />
                </th>
                <th className="hand" onClick={sort('aanmeldwijze')}>
                  Aanmeldwijze <FontAwesomeIcon icon={getSortIconByFieldName('aanmeldwijze')} />
                </th>
                <th className="hand" onClick={sort('deskundigheid')}>
                  Deskundigheid <FontAwesomeIcon icon={getSortIconByFieldName('deskundigheid')} />
                </th>
                <th className="hand" onClick={sort('isclientopdehoogte')}>
                  Isclientopdehoogte <FontAwesomeIcon icon={getSortIconByFieldName('isclientopdehoogte')} />
                </th>
                <th className="hand" onClick={sort('onderzoekswijze')}>
                  Onderzoekswijze <FontAwesomeIcon icon={getSortIconByFieldName('onderzoekswijze')} />
                </th>
                <th className="hand" onClick={sort('redenafsluiting')}>
                  Redenafsluiting <FontAwesomeIcon icon={getSortIconByFieldName('redenafsluiting')} />
                </th>
                <th className="hand" onClick={sort('vervolg')}>
                  Vervolg <FontAwesomeIcon icon={getSortIconByFieldName('vervolg')} />
                </th>
                <th className="hand" onClick={sort('verwezen')}>
                  Verwezen <FontAwesomeIcon icon={getSortIconByFieldName('verwezen')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {aommeldingwmojeugdList.map((aommeldingwmojeugd, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/aommeldingwmojeugd/${aommeldingwmojeugd.id}`} color="link" size="sm">
                      {aommeldingwmojeugd.id}
                    </Button>
                  </td>
                  <td>{aommeldingwmojeugd.aanmelder}</td>
                  <td>{aommeldingwmojeugd.aanmeldingdoor}</td>
                  <td>{aommeldingwmojeugd.aanmeldingdoorlandelijk}</td>
                  <td>{aommeldingwmojeugd.aanmeldwijze}</td>
                  <td>{aommeldingwmojeugd.deskundigheid}</td>
                  <td>{aommeldingwmojeugd.isclientopdehoogte}</td>
                  <td>{aommeldingwmojeugd.onderzoekswijze}</td>
                  <td>{aommeldingwmojeugd.redenafsluiting}</td>
                  <td>{aommeldingwmojeugd.vervolg}</td>
                  <td>{aommeldingwmojeugd.verwezen}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/aommeldingwmojeugd/${aommeldingwmojeugd.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/aommeldingwmojeugd/${aommeldingwmojeugd.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/aommeldingwmojeugd/${aommeldingwmojeugd.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Aommeldingwmojeugds found</div>
        )}
      </div>
    </div>
  );
};

export default Aommeldingwmojeugd;
