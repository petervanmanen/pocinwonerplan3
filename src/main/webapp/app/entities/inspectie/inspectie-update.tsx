import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVastgoedobject } from 'app/shared/model/vastgoedobject.model';
import { getEntities as getVastgoedobjects } from 'app/entities/vastgoedobject/vastgoedobject.reducer';
import { IInspectie } from 'app/shared/model/inspectie.model';
import { getEntity, updateEntity, createEntity, reset } from './inspectie.reducer';

export const InspectieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vastgoedobjects = useAppSelector(state => state.vastgoedobject.entities);
  const inspectieEntity = useAppSelector(state => state.inspectie.entity);
  const loading = useAppSelector(state => state.inspectie.loading);
  const updating = useAppSelector(state => state.inspectie.updating);
  const updateSuccess = useAppSelector(state => state.inspectie.updateSuccess);

  const handleClose = () => {
    navigate('/inspectie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getVastgoedobjects({}));
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
      ...inspectieEntity,
      ...values,
      betreftVastgoedobject: vastgoedobjects.find(it => it.id.toString() === values.betreftVastgoedobject?.toString()),
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
          ...inspectieEntity,
          betreftVastgoedobject: inspectieEntity?.betreftVastgoedobject?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.inspectie.home.createOrEditLabel" data-cy="InspectieCreateUpdateHeading">
            Create or edit a Inspectie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="inspectie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Aangemaaktdoor"
                id="inspectie-aangemaaktdoor"
                name="aangemaaktdoor"
                data-cy="aangemaaktdoor"
                type="text"
              />
              <ValidatedField label="Datumaanmaak" id="inspectie-datumaanmaak" name="datumaanmaak" data-cy="datumaanmaak" type="date" />
              <ValidatedField label="Datumgepland" id="inspectie-datumgepland" name="datumgepland" data-cy="datumgepland" type="date" />
              <ValidatedField
                label="Datuminspectie"
                id="inspectie-datuminspectie"
                name="datuminspectie"
                data-cy="datuminspectie"
                type="date"
              />
              <ValidatedField label="Datummutatie" id="inspectie-datummutatie" name="datummutatie" data-cy="datummutatie" type="date" />
              <ValidatedField label="Gemuteerddoor" id="inspectie-gemuteerddoor" name="gemuteerddoor" data-cy="gemuteerddoor" type="text" />
              <ValidatedField label="Inspectietype" id="inspectie-inspectietype" name="inspectietype" data-cy="inspectietype" type="text" />
              <ValidatedField
                label="Kenmerk"
                id="inspectie-kenmerk"
                name="kenmerk"
                data-cy="kenmerk"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Omschrijving" id="inspectie-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField label="Opmerkingen" id="inspectie-opmerkingen" name="opmerkingen" data-cy="opmerkingen" type="text" />
              <ValidatedField
                label="Status"
                id="inspectie-status"
                name="status"
                data-cy="status"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                id="inspectie-betreftVastgoedobject"
                name="betreftVastgoedobject"
                data-cy="betreftVastgoedobject"
                label="Betreft Vastgoedobject"
                type="select"
              >
                <option value="" key="0" />
                {vastgoedobjects
                  ? vastgoedobjects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/inspectie" replace color="info">
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

export default InspectieUpdate;
