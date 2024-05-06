import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVordering } from 'app/shared/model/vordering.model';
import { getEntity, updateEntity, createEntity, reset } from './vordering.reducer';

export const VorderingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vorderingEntity = useAppSelector(state => state.vordering.entity);
  const loading = useAppSelector(state => state.vordering.loading);
  const updating = useAppSelector(state => state.vordering.updating);
  const updateSuccess = useAppSelector(state => state.vordering.updateSuccess);

  const handleClose = () => {
    navigate('/vordering');
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
      ...vorderingEntity,
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
          ...vorderingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vordering.home.createOrEditLabel" data-cy="VorderingCreateUpdateHeading">
            Create or edit a Vordering
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vordering-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aangemaaktdoor"
                id="vordering-aangemaaktdoor"
                name="aangemaaktdoor"
                data-cy="aangemaaktdoor"
                type="text"
              />
              <ValidatedField label="Bedragbtw" id="vordering-bedragbtw" name="bedragbtw" data-cy="bedragbtw" type="text" />
              <ValidatedField label="Datumaanmaak" id="vordering-datumaanmaak" name="datumaanmaak" data-cy="datumaanmaak" type="date" />
              <ValidatedField label="Datummutatie" id="vordering-datummutatie" name="datummutatie" data-cy="datummutatie" type="date" />
              <ValidatedField label="Geaccordeerd" id="vordering-geaccordeerd" name="geaccordeerd" data-cy="geaccordeerd" type="text" />
              <ValidatedField
                label="Geaccordeerddoor"
                id="vordering-geaccordeerddoor"
                name="geaccordeerddoor"
                data-cy="geaccordeerddoor"
                type="text"
              />
              <ValidatedField
                label="Geaccordeerdop"
                id="vordering-geaccordeerdop"
                name="geaccordeerdop"
                data-cy="geaccordeerdop"
                type="date"
              />
              <ValidatedField label="Geexporteerd" id="vordering-geexporteerd" name="geexporteerd" data-cy="geexporteerd" type="text" />
              <ValidatedField label="Gemuteerddoor" id="vordering-gemuteerddoor" name="gemuteerddoor" data-cy="gemuteerddoor" type="text" />
              <ValidatedField label="Omschrijving" id="vordering-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Totaalbedrag" id="vordering-totaalbedrag" name="totaalbedrag" data-cy="totaalbedrag" type="text" />
              <ValidatedField
                label="Totaalbedraginclusief"
                id="vordering-totaalbedraginclusief"
                name="totaalbedraginclusief"
                data-cy="totaalbedraginclusief"
                type="text"
              />
              <ValidatedField
                label="Vorderingnummer"
                id="vordering-vorderingnummer"
                name="vorderingnummer"
                data-cy="vorderingnummer"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vordering" replace color="info">
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

export default VorderingUpdate;
