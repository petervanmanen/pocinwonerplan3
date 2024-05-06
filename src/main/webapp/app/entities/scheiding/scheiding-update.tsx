import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IScheiding } from 'app/shared/model/scheiding.model';
import { getEntity, updateEntity, createEntity, reset } from './scheiding.reducer';

export const ScheidingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const scheidingEntity = useAppSelector(state => state.scheiding.entity);
  const loading = useAppSelector(state => state.scheiding.loading);
  const updating = useAppSelector(state => state.scheiding.updating);
  const updateSuccess = useAppSelector(state => state.scheiding.updateSuccess);

  const handleClose = () => {
    navigate('/scheiding');
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
      ...scheidingEntity,
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
          ...scheidingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.scheiding.home.createOrEditLabel" data-cy="ScheidingCreateUpdateHeading">
            Create or edit a Scheiding
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="scheiding-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Aanleghoogte" id="scheiding-aanleghoogte" name="aanleghoogte" data-cy="aanleghoogte" type="text" />
              <ValidatedField label="Breedte" id="scheiding-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField label="Hoogte" id="scheiding-hoogte" name="hoogte" data-cy="hoogte" type="text" />
              <ValidatedField
                label="Jaaronderhouduitgevoerd"
                id="scheiding-jaaronderhouduitgevoerd"
                name="jaaronderhouduitgevoerd"
                data-cy="jaaronderhouduitgevoerd"
                type="text"
              />
              <ValidatedField label="Lengte" id="scheiding-lengte" name="lengte" data-cy="lengte" type="text" />
              <ValidatedField label="Leverancier" id="scheiding-leverancier" name="leverancier" data-cy="leverancier" type="text" />
              <ValidatedField label="Eobjectnaam" id="scheiding-eobjectnaam" name="eobjectnaam" data-cy="eobjectnaam" type="text" />
              <ValidatedField
                label="Eobjectnummer"
                id="scheiding-eobjectnummer"
                name="eobjectnummer"
                data-cy="eobjectnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Oppervlakte" id="scheiding-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <ValidatedField
                label="Scheidingmateriaal"
                id="scheiding-scheidingmateriaal"
                name="scheidingmateriaal"
                data-cy="scheidingmateriaal"
                type="text"
              />
              <ValidatedField
                label="Verplaatsbaar"
                id="scheiding-verplaatsbaar"
                name="verplaatsbaar"
                data-cy="verplaatsbaar"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/scheiding" replace color="info">
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

export default ScheidingUpdate;
