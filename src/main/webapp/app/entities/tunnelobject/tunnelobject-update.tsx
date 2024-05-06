import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITunnelobject } from 'app/shared/model/tunnelobject.model';
import { getEntity, updateEntity, createEntity, reset } from './tunnelobject.reducer';

export const TunnelobjectUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tunnelobjectEntity = useAppSelector(state => state.tunnelobject.entity);
  const loading = useAppSelector(state => state.tunnelobject.loading);
  const updating = useAppSelector(state => state.tunnelobject.updating);
  const updateSuccess = useAppSelector(state => state.tunnelobject.updateSuccess);

  const handleClose = () => {
    navigate('/tunnelobject');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...tunnelobjectEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...tunnelobjectEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.tunnelobject.home.createOrEditLabel" data-cy="TunnelobjectCreateUpdateHeading">
            Create or edit a Tunnelobject
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="tunnelobject-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aanleghoogte" id="tunnelobject-aanleghoogte" name="aanleghoogte" data-cy="aanleghoogte" type="text" />
              <ValidatedField
                label="Aantaltunnelbuizen"
                id="tunnelobject-aantaltunnelbuizen"
                name="aantaltunnelbuizen"
                data-cy="aantaltunnelbuizen"
                type="text"
              />
              <ValidatedField label="Breedte" id="tunnelobject-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField
                label="Doorrijbreedte"
                id="tunnelobject-doorrijbreedte"
                name="doorrijbreedte"
                data-cy="doorrijbreedte"
                type="text"
              />
              <ValidatedField
                label="Doorrijhoogte"
                id="tunnelobject-doorrijhoogte"
                name="doorrijhoogte"
                data-cy="doorrijhoogte"
                type="text"
              />
              <ValidatedField label="Hoogte" id="tunnelobject-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Jaarconserveren"
                id="tunnelobject-jaarconserveren"
                name="jaarconserveren"
                data-cy="jaarconserveren"
                type="text"
              />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="tunnelobject-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField label="Lengte" id="tunnelobject-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Leverancier" id="tunnelobject-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField label="Eobjectnaam" id="tunnelobject-eobjectnaam" name="eobjectnaam" data-cy="eobjectnaam" type="text" />
              <ValidatedField
                label="Eobjectnummer"
                id="tunnelobject-eobjectnummer"
                name="eobjectnummer"
                data-cy="eobjectnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Oppervlakte" id="tunnelobject-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <ValidatedField
                label="Tunnelobjectmateriaal"
                id="tunnelobject-tunnelobjectmateriaal"
                name="tunnelobjectmateriaal"
                data-cy="tunnelobjectmateriaal"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tunnelobject" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TunnelobjectUpdate;
