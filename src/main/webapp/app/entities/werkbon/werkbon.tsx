import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './werkbon.reducer';

export const Werkbon = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const werkbonList = useAppSelector(state => state.werkbon.entities);
  const loading = useAppSelector(state => state.werkbon.loading);

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
      <h2 id="werkbon-heading" data-cy="WerkbonHeading">
        Werkbons
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/werkbon/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Werkbon
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {werkbonList && werkbonList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th>
                  Betreft Vastgoedobject <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Bouwdeel <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Bouwdeelelement <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Hoortbij Inkooporder <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Voertwerkuitconform Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {werkbonList.map((werkbon, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/werkbon/${werkbon.id}`} color="link" size="sm">
                      {werkbon.id}
                    </Button>
                  </td>
                  <td>
                    {werkbon.betreftVastgoedobject ? (
                      <Link to={`/vastgoedobject/${werkbon.betreftVastgoedobject.id}`}>{werkbon.betreftVastgoedobject.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {werkbon.betreftBouwdeels
                      ? werkbon.betreftBouwdeels.map((val, j) => (
                          <span key={j}>
                            <Link to={`/bouwdeel/${val.id}`}>{val.id}</Link>
                            {j === werkbon.betreftBouwdeels.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {werkbon.betreftBouwdeelelements
                      ? werkbon.betreftBouwdeelelements.map((val, j) => (
                          <span key={j}>
                            <Link to={`/bouwdeelelement/${val.id}`}>{val.id}</Link>
                            {j === werkbon.betreftBouwdeelelements.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {werkbon.hoortbijInkooporder ? (
                      <Link to={`/inkooporder/${werkbon.hoortbijInkooporder.id}`}>{werkbon.hoortbijInkooporder.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {werkbon.voertwerkuitconformLeverancier ? (
                      <Link to={`/leverancier/${werkbon.voertwerkuitconformLeverancier.id}`}>
                        {werkbon.voertwerkuitconformLeverancier.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/werkbon/${werkbon.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/werkbon/${werkbon.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/werkbon/${werkbon.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Werkbons found</div>
        )}
      </div>
    </div>
  );
};

export default Werkbon;
