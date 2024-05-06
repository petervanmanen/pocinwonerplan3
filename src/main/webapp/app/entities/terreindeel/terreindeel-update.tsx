import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITerreindeel } from 'app/shared/model/terreindeel.model';
import { getEntity, updateEntity, createEntity, reset } from './terreindeel.reducer';

export const TerreindeelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const terreindeelEntity = useAppSelector(state => state.terreindeel.entity);
  const loading = useAppSelector(state => state.terreindeel.loading);
  const updating = useAppSelector(state => state.terreindeel.updating);
  const updateSuccess = useAppSelector(state => state.terreindeel.updateSuccess);

  const handleClose = () => {
    navigate('/terreindeel');
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
      ...terreindeelEntity,
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
          ...terreindeelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.terreindeel.home.createOrEditLabel" data-cy="TerreindeelCreateUpdateHeading">
            Create or edit a Terreindeel
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="terreindeel-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Breedte" id="terreindeel-breedte" name="breedte" data-cy="breedte" type="text" />
              <ValidatedField
                label="Cultuurhistorischwaardevol"
                id="terreindeel-cultuurhistorischwaardevol"
                name="cultuurhistorischwaardevol"
                data-cy="cultuurhistorischwaardevol"
                type="text"
              />
              <ValidatedField
                label="Herplantplicht"
                id="terreindeel-herplantplicht"
                name="herplantplicht"
                data-cy="herplantplicht"
                check
                type="checkbox"
              />
              <ValidatedField label="Oppervlakte" id="terreindeel-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <ValidatedField label="Optalud" id="terreindeel-optalud" name="optalud" data-cy="optalud" type="text" />
              <ValidatedField
                label="Percentageloofbos"
                id="terreindeel-percentageloofbos"
                name="percentageloofbos"
                data-cy="percentageloofbos"
                type="text"
              />
              <ValidatedField
                label="Terreindeelsoortnaam"
                id="terreindeel-terreindeelsoortnaam"
                name="terreindeelsoortnaam"
                data-cy="terreindeelsoortnaam"
                type="text"
              />
              <ValidatedField label="Type" id="terreindeel-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Typebewerking"
                id="terreindeel-typebewerking"
                name="typebewerking"
                data-cy="typebewerking"
                type="text"
              />
              <ValidatedField label="Typeplus" id="terreindeel-typeplus" name="typeplus" data-cy="typeplus" type="text" />
              <ValidatedField
                label="Typeplus 2"
                id="terreindeel-typeplus2"
                name="typeplus2"
                data-cy="typeplus2"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/terreindeel" replace color="info">
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

export default TerreindeelUpdate;
