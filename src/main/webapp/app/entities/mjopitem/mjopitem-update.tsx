import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMjopitem } from 'app/shared/model/mjopitem.model';
import { getEntity, updateEntity, createEntity, reset } from './mjopitem.reducer';

export const MjopitemUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const mjopitemEntity = useAppSelector(state => state.mjopitem.entity);
  const loading = useAppSelector(state => state.mjopitem.loading);
  const updating = useAppSelector(state => state.mjopitem.updating);
  const updateSuccess = useAppSelector(state => state.mjopitem.updateSuccess);

  const handleClose = () => {
    navigate('/mjopitem');
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
    if (values.kosten !== undefined && typeof values.kosten !== 'number') {
      values.kosten = Number(values.kosten);
    }

    const entity = {
      ...mjopitemEntity,
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
          ...mjopitemEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.mjopitem.home.createOrEditLabel" data-cy="MjopitemCreateUpdateHeading">
            Create or edit a Mjopitem
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="mjopitem-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Code" id="mjopitem-code" name="code" data-cy="code" type="text" />
              <ValidatedField label="Datumeinde" id="mjopitem-datumeinde" name="datumeinde" data-cy="datumeinde" type="date" />
              <ValidatedField
                label="Datumopzeggingaanbieder"
                id="mjopitem-datumopzeggingaanbieder"
                name="datumopzeggingaanbieder"
                data-cy="datumopzeggingaanbieder"
                type="date"
              />
              <ValidatedField
                label="Datumopzeggingontvanger"
                id="mjopitem-datumopzeggingontvanger"
                name="datumopzeggingontvanger"
                data-cy="datumopzeggingontvanger"
                type="date"
              />
              <ValidatedField label="Datumstart" id="mjopitem-datumstart" name="datumstart" data-cy="datumstart" type="date" />
              <ValidatedField label="Kosten" id="mjopitem-kosten" name="kosten" data-cy="kosten" type="text" />
              <ValidatedField label="Omschrijving" id="mjopitem-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Opzegtermijnaanbieder"
                id="mjopitem-opzegtermijnaanbieder"
                name="opzegtermijnaanbieder"
                data-cy="opzegtermijnaanbieder"
                type="text"
              />
              <ValidatedField
                label="Opzegtermijnontvanger"
                id="mjopitem-opzegtermijnontvanger"
                name="opzegtermijnontvanger"
                data-cy="opzegtermijnontvanger"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mjopitem" replace color="info">
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

export default MjopitemUpdate;
