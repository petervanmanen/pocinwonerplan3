import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISubsidiecomponent } from 'app/shared/model/subsidiecomponent.model';
import { getEntities as getSubsidiecomponents } from 'app/entities/subsidiecomponent/subsidiecomponent.reducer';
import { IBetaalmoment } from 'app/shared/model/betaalmoment.model';
import { getEntity, updateEntity, createEntity, reset } from './betaalmoment.reducer';

export const BetaalmomentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const subsidiecomponents = useAppSelector(state => state.subsidiecomponent.entities);
  const betaalmomentEntity = useAppSelector(state => state.betaalmoment.entity);
  const loading = useAppSelector(state => state.betaalmoment.loading);
  const updating = useAppSelector(state => state.betaalmoment.updating);
  const updateSuccess = useAppSelector(state => state.betaalmoment.updateSuccess);

  const handleClose = () => {
    navigate('/betaalmoment');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSubsidiecomponents({}));
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
    if (values.bedrag !== undefined && typeof values.bedrag !== 'number') {
      values.bedrag = Number(values.bedrag);
    }

    const entity = {
      ...betaalmomentEntity,
      ...values,
      heeftSubsidiecomponent: subsidiecomponents.find(it => it.id.toString() === values.heeftSubsidiecomponent?.toString()),
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
          ...betaalmomentEntity,
          heeftSubsidiecomponent: betaalmomentEntity?.heeftSubsidiecomponent?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.betaalmoment.home.createOrEditLabel" data-cy="BetaalmomentCreateUpdateHeading">
            Create or edit a Betaalmoment
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="betaalmoment-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Bedrag" id="betaalmoment-bedrag" name="bedrag" data-cy="bedrag" type="text" />
              <ValidatedField label="Datum" id="betaalmoment-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField label="Voorschot" id="betaalmoment-voorschot" name="voorschot" data-cy="voorschot" check type="checkbox" />
              <ValidatedField
                id="betaalmoment-heeftSubsidiecomponent"
                name="heeftSubsidiecomponent"
                data-cy="heeftSubsidiecomponent"
                label="Heeft Subsidiecomponent"
                type="select"
                required
              >
                <option value="" key="0" />
                {subsidiecomponents
                  ? subsidiecomponents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/betaalmoment" replace color="info">
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

export default BetaalmomentUpdate;
