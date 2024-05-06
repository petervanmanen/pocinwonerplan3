import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProducttype } from 'app/shared/model/producttype.model';
import { getEntities as getProducttypes } from 'app/entities/producttype/producttype.reducer';
import { IKlantbeoordeling } from 'app/shared/model/klantbeoordeling.model';
import { getEntities as getKlantbeoordelings } from 'app/entities/klantbeoordeling/klantbeoordeling.reducer';
import { IHeffing } from 'app/shared/model/heffing.model';
import { getEntities as getHeffings } from 'app/entities/heffing/heffing.reducer';
import { IProject } from 'app/shared/model/project.model';
import { getEntities as getProjects } from 'app/entities/project/project.reducer';
import { IZaaktype } from 'app/shared/model/zaaktype.model';
import { getEntities as getZaaktypes } from 'app/entities/zaaktype/zaaktype.reducer';
import { IDocument } from 'app/shared/model/document.model';
import { getEntities as getDocuments } from 'app/entities/document/document.reducer';
import { IMedewerker } from 'app/shared/model/medewerker.model';
import { getEntities as getMedewerkers } from 'app/entities/medewerker/medewerker.reducer';
import { IGrondslag } from 'app/shared/model/grondslag.model';
import { getEntities as getGrondslags } from 'app/entities/grondslag/grondslag.reducer';
import { IBedrijfsproces } from 'app/shared/model/bedrijfsproces.model';
import { getEntities as getBedrijfsproces } from 'app/entities/bedrijfsproces/bedrijfsproces.reducer';
import { IBetrokkene } from 'app/shared/model/betrokkene.model';
import { getEntities as getBetrokkenes } from 'app/entities/betrokkene/betrokkene.reducer';
import { IZaak } from 'app/shared/model/zaak.model';
import { getEntity, updateEntity, createEntity, reset } from './zaak.reducer';

export const ZaakUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const producttypes = useAppSelector(state => state.producttype.entities);
  const klantbeoordelings = useAppSelector(state => state.klantbeoordeling.entities);
  const heffings = useAppSelector(state => state.heffing.entities);
  const projects = useAppSelector(state => state.project.entities);
  const zaaktypes = useAppSelector(state => state.zaaktype.entities);
  const documents = useAppSelector(state => state.document.entities);
  const medewerkers = useAppSelector(state => state.medewerker.entities);
  const grondslags = useAppSelector(state => state.grondslag.entities);
  const bedrijfsproces = useAppSelector(state => state.bedrijfsproces.entities);
  const betrokkenes = useAppSelector(state => state.betrokkene.entities);
  const zaakEntity = useAppSelector(state => state.zaak.entity);
  const loading = useAppSelector(state => state.zaak.loading);
  const updating = useAppSelector(state => state.zaak.updating);
  const updateSuccess = useAppSelector(state => state.zaak.updateSuccess);

  const handleClose = () => {
    navigate('/zaak');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getProducttypes({}));
    dispatch(getKlantbeoordelings({}));
    dispatch(getHeffings({}));
    dispatch(getProjects({}));
    dispatch(getZaaktypes({}));
    dispatch(getDocuments({}));
    dispatch(getMedewerkers({}));
    dispatch(getGrondslags({}));
    dispatch(getBedrijfsproces({}));
    dispatch(getBetrokkenes({}));
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
      ...zaakEntity,
      ...values,
      heeftproductProducttype: producttypes.find(it => it.id.toString() === values.heeftproductProducttype?.toString()),
      heeftKlantbeoordeling: klantbeoordelings.find(it => it.id.toString() === values.heeftKlantbeoordeling?.toString()),
      heeftHeffing: heffings.find(it => it.id.toString() === values.heeftHeffing?.toString()),
      betreftProject: projects.find(it => it.id.toString() === values.betreftProject?.toString()),
      isvanZaaktype: zaaktypes.find(it => it.id.toString() === values.isvanZaaktype?.toString()),
      kentDocuments: mapIdList(values.kentDocuments),
      afhandelendmedewerkerMedewerkers: mapIdList(values.afhandelendmedewerkerMedewerkers),
      heeftGrondslags: mapIdList(values.heeftGrondslags),
      uitgevoerdbinnenBedrijfsproces: mapIdList(values.uitgevoerdbinnenBedrijfsproces),
      oefentuitBetrokkenes: mapIdList(values.oefentuitBetrokkenes),
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
          ...zaakEntity,
          heeftproductProducttype: zaakEntity?.heeftproductProducttype?.id,
          heeftKlantbeoordeling: zaakEntity?.heeftKlantbeoordeling?.id,
          heeftHeffing: zaakEntity?.heeftHeffing?.id,
          betreftProject: zaakEntity?.betreftProject?.id,
          isvanZaaktype: zaakEntity?.isvanZaaktype?.id,
          kentDocuments: zaakEntity?.kentDocuments?.map(e => e.id.toString()),
          afhandelendmedewerkerMedewerkers: zaakEntity?.afhandelendmedewerkerMedewerkers?.map(e => e.id.toString()),
          heeftGrondslags: zaakEntity?.heeftGrondslags?.map(e => e.id.toString()),
          uitgevoerdbinnenBedrijfsproces: zaakEntity?.uitgevoerdbinnenBedrijfsproces?.map(e => e.id.toString()),
          oefentuitBetrokkenes: zaakEntity?.oefentuitBetrokkenes?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.zaak.home.createOrEditLabel" data-cy="ZaakCreateUpdateHeading">
            Create or edit a Zaak
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="zaak-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Empty" id="zaak-empty" name="empty" data-cy="empty" type="text" />
              <ValidatedField
                label="Archiefnominatie"
                id="zaak-archiefnominatie"
                name="archiefnominatie"
                data-cy="archiefnominatie"
                type="text"
              />
              <ValidatedField label="Datumeinde" id="zaak-datumeinde" name="datumeinde" data-cy="datumeinde" type="text" />
              <ValidatedField
                label="Datumeindegepland"
                id="zaak-datumeindegepland"
                name="datumeindegepland"
                data-cy="datumeindegepland"
                type="text"
              />
              <ValidatedField
                label="Datumeindeuiterlijkeafdoening"
                id="zaak-datumeindeuiterlijkeafdoening"
                name="datumeindeuiterlijkeafdoening"
                data-cy="datumeindeuiterlijkeafdoening"
                type="text"
              />
              <ValidatedField
                label="Datumlaatstebetaling"
                id="zaak-datumlaatstebetaling"
                name="datumlaatstebetaling"
                data-cy="datumlaatstebetaling"
                type="text"
              />
              <ValidatedField
                label="Datumpublicatie"
                id="zaak-datumpublicatie"
                name="datumpublicatie"
                data-cy="datumpublicatie"
                type="text"
              />
              <ValidatedField
                label="Datumregistratie"
                id="zaak-datumregistratie"
                name="datumregistratie"
                data-cy="datumregistratie"
                type="text"
              />
              <ValidatedField label="Datumstart" id="zaak-datumstart" name="datumstart" data-cy="datumstart" type="text" />
              <ValidatedField
                label="Datumvernietigingdossier"
                id="zaak-datumvernietigingdossier"
                name="datumvernietigingdossier"
                data-cy="datumvernietigingdossier"
                type="text"
              />
              <ValidatedField label="Document" id="zaak-document" name="document" data-cy="document" type="text" />
              <ValidatedField label="Duurverlenging" id="zaak-duurverlenging" name="duurverlenging" data-cy="duurverlenging" type="text" />
              <ValidatedField
                label="Indicatiebetaling"
                id="zaak-indicatiebetaling"
                name="indicatiebetaling"
                data-cy="indicatiebetaling"
                type="text"
              />
              <ValidatedField
                label="Indicatiedeelzaken"
                id="zaak-indicatiedeelzaken"
                name="indicatiedeelzaken"
                data-cy="indicatiedeelzaken"
                type="text"
              />
              <ValidatedField
                label="Indicatieopschorting"
                id="zaak-indicatieopschorting"
                name="indicatieopschorting"
                data-cy="indicatieopschorting"
                type="text"
              />
              <ValidatedField
                label="Leges"
                id="zaak-leges"
                name="leges"
                data-cy="leges"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField label="Omschrijving" id="zaak-omschrijving" name="omschrijving" data-cy="omschrijving" type="text" />
              <ValidatedField
                label="Omschrijvingresultaat"
                id="zaak-omschrijvingresultaat"
                name="omschrijvingresultaat"
                data-cy="omschrijvingresultaat"
                type="text"
              />
              <ValidatedField
                label="Redenopschorting"
                id="zaak-redenopschorting"
                name="redenopschorting"
                data-cy="redenopschorting"
                type="text"
              />
              <ValidatedField
                label="Redenverlenging"
                id="zaak-redenverlenging"
                name="redenverlenging"
                data-cy="redenverlenging"
                type="text"
              />
              <ValidatedField label="Toelichting" id="zaak-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <ValidatedField
                label="Toelichtingresultaat"
                id="zaak-toelichtingresultaat"
                name="toelichtingresultaat"
                data-cy="toelichtingresultaat"
                type="text"
              />
              <ValidatedField
                label="Vertrouwelijkheid"
                id="zaak-vertrouwelijkheid"
                name="vertrouwelijkheid"
                data-cy="vertrouwelijkheid"
                type="text"
              />
              <ValidatedField
                label="Zaakidentificatie"
                id="zaak-zaakidentificatie"
                name="zaakidentificatie"
                data-cy="zaakidentificatie"
                type="text"
              />
              <ValidatedField label="Zaakniveau" id="zaak-zaakniveau" name="zaakniveau" data-cy="zaakniveau" type="text" />
              <ValidatedField
                id="zaak-heeftproductProducttype"
                name="heeftproductProducttype"
                data-cy="heeftproductProducttype"
                label="Heeftproduct Producttype"
                type="select"
                required
              >
                <option value="" key="0" />
                {producttypes
                  ? producttypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="zaak-heeftKlantbeoordeling"
                name="heeftKlantbeoordeling"
                data-cy="heeftKlantbeoordeling"
                label="Heeft Klantbeoordeling"
                type="select"
                required
              >
                <option value="" key="0" />
                {klantbeoordelings
                  ? klantbeoordelings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="zaak-heeftHeffing"
                name="heeftHeffing"
                data-cy="heeftHeffing"
                label="Heeft Heffing"
                type="select"
                required
              >
                <option value="" key="0" />
                {heffings
                  ? heffings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <ValidatedField id="zaak-betreftProject" name="betreftProject" data-cy="betreftProject" label="Betreft Project" type="select">
                <option value="" key="0" />
                {projects
                  ? projects.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="zaak-isvanZaaktype" name="isvanZaaktype" data-cy="isvanZaaktype" label="Isvan Zaaktype" type="select">
                <option value="" key="0" />
                {zaaktypes
                  ? zaaktypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Kent Document"
                id="zaak-kentDocument"
                data-cy="kentDocument"
                type="select"
                multiple
                name="kentDocuments"
              >
                <option value="" key="0" />
                {documents
                  ? documents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Afhandelendmedewerker Medewerker"
                id="zaak-afhandelendmedewerkerMedewerker"
                data-cy="afhandelendmedewerkerMedewerker"
                type="select"
                multiple
                name="afhandelendmedewerkerMedewerkers"
              >
                <option value="" key="0" />
                {medewerkers
                  ? medewerkers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Heeft Grondslag"
                id="zaak-heeftGrondslag"
                data-cy="heeftGrondslag"
                type="select"
                multiple
                name="heeftGrondslags"
              >
                <option value="" key="0" />
                {grondslags
                  ? grondslags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Uitgevoerdbinnen Bedrijfsproces"
                id="zaak-uitgevoerdbinnenBedrijfsproces"
                data-cy="uitgevoerdbinnenBedrijfsproces"
                type="select"
                multiple
                name="uitgevoerdbinnenBedrijfsproces"
              >
                <option value="" key="0" />
                {bedrijfsproces
                  ? bedrijfsproces.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label="Oefentuit Betrokkene"
                id="zaak-oefentuitBetrokkene"
                data-cy="oefentuitBetrokkene"
                type="select"
                multiple
                name="oefentuitBetrokkenes"
              >
                <option value="" key="0" />
                {betrokkenes
                  ? betrokkenes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/zaak" replace color="info">
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

export default ZaakUpdate;
