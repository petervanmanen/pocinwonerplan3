import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAutoriteitafgiftenederlandsreisdocument } from 'app/shared/model/autoriteitafgiftenederlandsreisdocument.model';
import { getEntity, updateEntity, createEntity, reset } from './autoriteitafgiftenederlandsreisdocument.reducer';

export const AutoriteitafgiftenederlandsreisdocumentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const autoriteitafgiftenederlandsreisdocumentEntity = useAppSelector(state => state.autoriteitafgiftenederlandsreisdocument.entity);
  const loading = useAppSelector(state => state.autoriteitafgiftenederlandsreisdocument.loading);
  const updating = useAppSelector(state => state.autoriteitafgiftenederlandsreisdocument.updating);
  const updateSuccess = useAppSelector(state => state.autoriteitafgiftenederlandsreisdocument.updateSuccess);

  const handleClose = () => {
    navigate('/autoriteitafgiftenederlandsreisdocument');
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
      ...autoriteitafgiftenederlandsreisdocumentEntity,
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
          ...autoriteitafgiftenederlandsreisdocumentEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="demo3App.autoriteitafgiftenederlandsreisdocument.home.createOrEditLabel"
            data-cy="AutoriteitafgiftenederlandsreisdocumentCreateUpdateHeading"
          >
            Create or edit a Autoriteitafgiftenederlandsreisdocument
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
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="autoriteitafgiftenederlandsreisdocument-id"
                  label="ID"
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label="Code" id="autoriteitafgiftenederlandsreisdocument-code" name="code" data-cy="code" type="text" />
              <ValidatedField
                label="Datumbegingeldigheidautoriteitvanafgifte"
                id="autoriteitafgiftenederlandsreisdocument-datumbegingeldigheidautoriteitvanafgifte"
                name="datumbegingeldigheidautoriteitvanafgifte"
                data-cy="datumbegingeldigheidautoriteitvanafgifte"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldigheidautoriteitvanafgifte"
                id="autoriteitafgiftenederlandsreisdocument-datumeindegeldigheidautoriteitvanafgifte"
                name="datumeindegeldigheidautoriteitvanafgifte"
                data-cy="datumeindegeldigheidautoriteitvanafgifte"
                type="date"
              />
              <ValidatedField
                label="Omschrijving"
                id="autoriteitafgiftenederlandsreisdocument-omschrijving"
                name="omschrijving"
                data-cy="omschrijving"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/autoriteitafgiftenederlandsreisdocument"
                replace
                color="info"
              >
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

export default AutoriteitafgiftenederlandsreisdocumentUpdate;
