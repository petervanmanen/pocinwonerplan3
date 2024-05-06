import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './vegetatieobject.reducer';

export const Vegetatieobject = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vegetatieobjectList = useAppSelector(state => state.vegetatieobject.entities);
  const loading = useAppSelector(state => state.vegetatieobject.loading);

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
      <h2 id="vegetatieobject-heading" data-cy="VegetatieobjectHeading">
        Vegetatieobjects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vegetatieobject/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vegetatieobject
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vegetatieobjectList && vegetatieobjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('afvoeren')}>
                  Afvoeren <FontAwesomeIcon icon={getSortIconByFieldName('afvoeren')} />
                </th>
                <th className="hand" onClick={sort('bereikbaarheid')}>
                  Bereikbaarheid <FontAwesomeIcon icon={getSortIconByFieldName('bereikbaarheid')} />
                </th>
                <th className="hand" onClick={sort('ecologischbeheer')}>
                  Ecologischbeheer <FontAwesomeIcon icon={getSortIconByFieldName('ecologischbeheer')} />
                </th>
                <th className="hand" onClick={sort('kwaliteitsniveauactueel')}>
                  Kwaliteitsniveauactueel <FontAwesomeIcon icon={getSortIconByFieldName('kwaliteitsniveauactueel')} />
                </th>
                <th className="hand" onClick={sort('kwaliteitsniveaugewenst')}>
                  Kwaliteitsniveaugewenst <FontAwesomeIcon icon={getSortIconByFieldName('kwaliteitsniveaugewenst')} />
                </th>
                <th className="hand" onClick={sort('kweker')}>
                  Kweker <FontAwesomeIcon icon={getSortIconByFieldName('kweker')} />
                </th>
                <th className="hand" onClick={sort('leverancier')}>
                  Leverancier <FontAwesomeIcon icon={getSortIconByFieldName('leverancier')} />
                </th>
                <th className="hand" onClick={sort('eobjectnummer')}>
                  Eobjectnummer <FontAwesomeIcon icon={getSortIconByFieldName('eobjectnummer')} />
                </th>
                <th className="hand" onClick={sort('soortnaam')}>
                  Soortnaam <FontAwesomeIcon icon={getSortIconByFieldName('soortnaam')} />
                </th>
                <th className="hand" onClick={sort('typestandplaats')}>
                  Typestandplaats <FontAwesomeIcon icon={getSortIconByFieldName('typestandplaats')} />
                </th>
                <th className="hand" onClick={sort('typestandplaatsplus')}>
                  Typestandplaatsplus <FontAwesomeIcon icon={getSortIconByFieldName('typestandplaatsplus')} />
                </th>
                <th className="hand" onClick={sort('vegetatieobjectbereikbaarheidplus')}>
                  Vegetatieobjectbereikbaarheidplus <FontAwesomeIcon icon={getSortIconByFieldName('vegetatieobjectbereikbaarheidplus')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vegetatieobjectList.map((vegetatieobject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vegetatieobject/${vegetatieobject.id}`} color="link" size="sm">
                      {vegetatieobject.id}
                    </Button>
                  </td>
                  <td>{vegetatieobject.afvoeren ? 'true' : 'false'}</td>
                  <td>{vegetatieobject.bereikbaarheid}</td>
                  <td>{vegetatieobject.ecologischbeheer ? 'true' : 'false'}</td>
                  <td>{vegetatieobject.kwaliteitsniveauactueel}</td>
                  <td>{vegetatieobject.kwaliteitsniveaugewenst}</td>
                  <td>{vegetatieobject.kweker}</td>
                  <td>{vegetatieobject.leverancier}</td>
                  <td>{vegetatieobject.eobjectnummer}</td>
                  <td>{vegetatieobject.soortnaam}</td>
                  <td>{vegetatieobject.typestandplaats}</td>
                  <td>{vegetatieobject.typestandplaatsplus}</td>
                  <td>{vegetatieobject.vegetatieobjectbereikbaarheidplus}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vegetatieobject/${vegetatieobject.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/vegetatieobject/${vegetatieobject.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vegetatieobject/${vegetatieobject.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vegetatieobjects found</div>
        )}
      </div>
    </div>
  );
};

export default Vegetatieobject;
