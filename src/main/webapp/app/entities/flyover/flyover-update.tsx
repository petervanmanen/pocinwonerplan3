import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFlyover } from 'app/shared/model/flyover.model';
import { getEntity, updateEntity, createEntity, reset } from './flyover.reducer';

export const FlyoverUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const flyoverEntity = useAppSelector(state => state.flyover.entity);
  const loading = useAppSelector(state => state.flyover.loading);
  const updating = useAppSelector(state => state.flyover.updating);
  const updateSuccess = useAppSelector(state => state.flyover.updateSuccess);

  const handleClose = () => {
    navigate('/flyover');
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
      ...flyoverEntity,
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
          ...flyoverEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.flyover.home.createOrEditLabel" data-cy="FlyoverCreateUpdateHeading">
            Create or edit a Flyover
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="flyover-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aantaloverspanningen"
                id="flyover-aantaloverspanningen"
                name="aantaloverspanningen"
                data-cy="aantaloverspanningen"
                type="text"
              />
              <ValidatedField
                label="Belastingklassenieuw"
                id="flyover-belastingklassenieuw"
                name="belastingklassenieuw"
                data-cy="belastingklassenieuw"
                type="text"
              />
              <ValidatedField
                label="Belastingklasseoud"
                id="flyover-belastingklasseoud"
                name="belastingklasseoud"
                data-cy="belastingklasseoud"
                type="text"
              />
              <ValidatedField label="Draagvermogen" id="flyover-draagvermogen" name="draagvermogen" data-cy="draagvermogen" type="text" />
              <ValidatedField
                label="Maximaaltoelaatbaarvoertuiggewicht"
                id="flyover-maximaaltoelaatbaarvoertuiggewicht"
                name="maximaaltoelaatbaarvoertuiggewicht"
                data-cy="maximaaltoelaatbaarvoertuiggewicht"
                type="text"
              />
              <ValidatedField
                label="Maximaleasbelasting"
                id="flyover-maximaleasbelasting"
                name="maximaleasbelasting"
                data-cy="maximaleasbelasting"
                type="text"
              />
              <ValidatedField
                label="Maximaleoverspanning"
                id="flyover-maximaleoverspanning"
                name="maximaleoverspanning"
                data-cy="maximaleoverspanning"
                type="text"
              />
              <ValidatedField
                label="Overbruggingsobjectdoorrijopening"
                id="flyover-overbruggingsobjectdoorrijopening"
                name="overbruggingsobjectdoorrijopening"
                data-cy="overbruggingsobjectdoorrijopening"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField label="Type" id="flyover-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                label="Zwaarstevoertuig"
                id="flyover-zwaarstevoertuig"
                name="zwaarstevoertuig"
                data-cy="zwaarstevoertuig"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/flyover" replace color="info">
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

export default FlyoverUpdate;
