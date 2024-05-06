import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IBeschermdestatus } from 'app/shared/model/beschermdestatus.model';
import { getEntity, updateEntity, createEntity, reset } from './beschermdestatus.reducer';

export const BeschermdestatusUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const beschermdestatusEntity = useAppSelector(state => state.beschermdestatus.entity);
  const loading = useAppSelector(state => state.beschermdestatus.loading);
  const updating = useAppSelector(state => state.beschermdestatus.updating);
  const updateSuccess = useAppSelector(state => state.beschermdestatus.updateSuccess);

  const handleClose = () => {
    navigate('/beschermdestatus');
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
      ...beschermdestatusEntity,
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
          ...beschermdestatusEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.beschermdestatus.home.createOrEditLabel" data-cy="BeschermdestatusCreateUpdateHeading">
            Create or edit a Beschermdestatus
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="beschermdestatus-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Bronnen" id="beschermdestatus-bronnen" name="bronnen" data-cy="bronnen" type="text" />
              <ValidatedField label="Complex" id="beschermdestatus-complex" name="complex" data-cy="complex" type="text" />
              <ValidatedField
                label="Datuminschrijvingregister"
                id="beschermdestatus-datuminschrijvingregister"
                name="datuminschrijvingregister"
                data-cy="datuminschrijvingregister"
                type="date"
              />
              <ValidatedField
                label="Gemeentelijkmonumentcode"
                id="beschermdestatus-gemeentelijkmonumentcode"
                name="gemeentelijkmonumentcode"
                data-cy="gemeentelijkmonumentcode"
                type="text"
              />
              <ValidatedField
                label="Gezichtscode"
                id="beschermdestatus-gezichtscode"
                name="gezichtscode"
                data-cy="gezichtscode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Naam" id="beschermdestatus-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField
                label="Omschrijving"
                id="beschermdestatus-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField label="Opmerkingen" id="beschermdestatus-opmerkingen" name="opmerkingen" data-cy="opmerkingen" type="text" />
              <ValidatedField
                label="Rijksmonumentcode"
                id="beschermdestatus-rijksmonumentcode"
                name="rijksmonumentcode"
                data-cy="rijksmonumentcode"
                type="text"
              />
              <ValidatedField label="Type" id="beschermdestatus-type" name="type" data-cy="type" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/beschermdestatus" replace color="info">
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

export default BeschermdestatusUpdate;
