import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMuseumrelatie } from 'app/shared/model/museumrelatie.model';
import { getEntities as getMuseumrelaties } from 'app/entities/museumrelatie/museumrelatie.reducer';
import { IMailing } from 'app/shared/model/mailing.model';
import { getEntity, updateEntity, createEntity, reset } from './mailing.reducer';

export const MailingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const museumrelaties = useAppSelector(state => state.museumrelatie.entities);
  const mailingEntity = useAppSelector(state => state.mailing.entity);
  const loading = useAppSelector(state => state.mailing.loading);
  const updating = useAppSelector(state => state.mailing.updating);
  const updateSuccess = useAppSelector(state => state.mailing.updateSuccess);

  const handleClose = () => {
    navigate('/mailing');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getMuseumrelaties({}));
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
      ...mailingEntity,
      ...values,
      versturenaanMuseumrelaties: mapIdList(values.versturenaanMuseumrelaties),
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
          ...mailingEntity,
          versturenaanMuseumrelaties: mailingEntity?.versturenaanMuseumrelaties?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.mailing.home.createOrEditLabel" data-cy="MailingCreateUpdateHeading">
            Create or edit a Mailing
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="mailing-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Datum" id="mailing-datum" name="datum" data-cy="datum" type="date" />
              <ValidatedField label="Naam" id="mailing-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Omschrijving" id="mailing-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Versturenaan Museumrelatie"
                id="mailing-versturenaanMuseumrelatie"
                data-cy="versturenaanMuseumrelatie"
                type="select"
                multiple
                name="versturenaanMuseumrelaties"
              >
                <option value="" key="0" />
                {museumrelaties
                  ? museumrelaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/mailing" replace color="info">
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

export default MailingUpdate;
