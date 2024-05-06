import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAanvraaginkooporder } from 'app/shared/model/aanvraaginkooporder.model';
import { getEntity, updateEntity, createEntity, reset } from './aanvraaginkooporder.reducer';

export const AanvraaginkooporderUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aanvraaginkooporderEntity = useAppSelector(state => state.aanvraaginkooporder.entity);
  const loading = useAppSelector(state => state.aanvraaginkooporder.loading);
  const updating = useAppSelector(state => state.aanvraaginkooporder.updating);
  const updateSuccess = useAppSelector(state => state.aanvraaginkooporder.updateSuccess);

  const handleClose = () => {
    navigate('/aanvraaginkooporder');
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
    if (values.nettototaalbedrag !== undefined && typeof values.nettototaalbedrag !== 'number') {
      values.nettototaalbedrag = Number(values.nettototaalbedrag);
    }

    const entity = {
      ...aanvraaginkooporderEntity,
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
          ...aanvraaginkooporderEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aanvraaginkooporder.home.createOrEditLabel" data-cy="AanvraaginkooporderCreateUpdateHeading">
            Create or edit a Aanvraaginkooporder
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
                <ValidatedField name="id" required readOnly id="aanvraaginkooporder-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Betalingovermeerjaren"
                id="aanvraaginkooporder-betalingovermeerjaren"
                name="betalingovermeerjaren"
                data-cy="betalingovermeerjaren"
                type="text"
              />
              <ValidatedField
                label="Correspondentienummer"
                id="aanvraaginkooporder-correspondentienummer"
                name="correspondentienummer"
                data-cy="correspondentienummer"
                type="text"
              />
              <ValidatedField
                label="Inhuuranders"
                id="aanvraaginkooporder-inhuuranders"
                name="inhuuranders"
                data-cy="inhuuranders"
                type="text"
              />
              <ValidatedField
                label="Leveringofdienst"
                id="aanvraaginkooporder-leveringofdienst"
                name="leveringofdienst"
                data-cy="leveringofdienst"
                type="text"
              />
              <ValidatedField
                label="Nettototaalbedrag"
                id="aanvraaginkooporder-nettototaalbedrag"
                name="nettototaalbedrag"
                data-cy="nettototaalbedrag"
                type="text"
              />
              <ValidatedField
                label="Omschrijving"
                id="aanvraaginkooporder-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <ValidatedField label="Onderwerp" id="aanvraaginkooporder-onderwerp" name="onderwerp" data-cy="onderwerp" type="text" />
              <ValidatedField label="Reactie" id="aanvraaginkooporder-reactie" name="reactie" data-cy="reactie" type="text" />
              <ValidatedField label="Status" id="aanvraaginkooporder-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Wijzevaninhuur"
                id="aanvraaginkooporder-wijzevaninhuur"
                name="wijzevaninhuur"
                data-cy="wijzevaninhuur"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aanvraaginkooporder" replace color="info">
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

export default AanvraaginkooporderUpdate;
