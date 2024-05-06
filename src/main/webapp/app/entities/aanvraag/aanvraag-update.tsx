import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IArchiefstuk } from 'app/shared/model/archiefstuk.model';
import { getEntities as getArchiefstuks } from 'app/entities/archiefstuk/archiefstuk.reducer';
import { IBezoeker } from 'app/shared/model/bezoeker.model';
import { getEntities as getBezoekers } from 'app/entities/bezoeker/bezoeker.reducer';
import { IAanvraag } from 'app/shared/model/aanvraag.model';
import { getEntity, updateEntity, createEntity, reset } from './aanvraag.reducer';

export const AanvraagUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const archiefstuks = useAppSelector(state => state.archiefstuk.entities);
  const bezoekers = useAppSelector(state => state.bezoeker.entities);
  const aanvraagEntity = useAppSelector(state => state.aanvraag.entity);
  const loading = useAppSelector(state => state.aanvraag.loading);
  const updating = useAppSelector(state => state.aanvraag.updating);
  const updateSuccess = useAppSelector(state => state.aanvraag.updateSuccess);

  const handleClose = () => {
    navigate('/aanvraag');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getArchiefstuks({}));
    dispatch(getBezoekers({}));
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
      ...aanvraagEntity,
      ...values,
      voorArchiefstuks: mapIdList(values.voorArchiefstuks),
      doetBezoeker: bezoekers.find(it => it.id.toString() === values.doetBezoeker?.toString()),
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
          ...aanvraagEntity,
          voorArchiefstuks: aanvraagEntity?.voorArchiefstuks?.map(e => e.id.toString()),
          doetBezoeker: aanvraagEntity?.doetBezoeker?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aanvraag.home.createOrEditLabel" data-cy="AanvraagCreateUpdateHeading">
            Create or edit a Aanvraag
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="aanvraag-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datumtijd" id="aanvraag-datumtijd" name="datumtijd" data-cy="datumtijd" type="text" />
              <ValidatedField
                label="Voor Archiefstuk"
                id="aanvraag-voorArchiefstuk"
                data-cy="voorArchiefstuk"
                type="select"
                multiple
                name="voorArchiefstuks"
              >
                <option value="" key="0" />
                {archiefstuks
                  ? archiefstuks.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="aanvraag-doetBezoeker" name="doetBezoeker" data-cy="doetBezoeker" label="Doet Bezoeker" type="select">
                <option value="" key="0" />
                {bezoekers
                  ? bezoekers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aanvraag" replace color="info">
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

export default AanvraagUpdate;
