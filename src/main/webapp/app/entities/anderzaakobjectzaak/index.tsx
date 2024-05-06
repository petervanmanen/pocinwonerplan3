import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Anderzaakobjectzaak from './anderzaakobjectzaak';
import AnderzaakobjectzaakDetail from './anderzaakobjectzaak-detail';
import AnderzaakobjectzaakUpdate from './anderzaakobjectzaak-update';
import AnderzaakobjectzaakDeleteDialog from './anderzaakobjectzaak-delete-dialog';

const AnderzaakobjectzaakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Anderzaakobjectzaak />} />
    <Route path="new" element={<AnderzaakobjectzaakUpdate />} />
    <Route path=":id">
      <Route index element={<AnderzaakobjectzaakDetail />} />
      <Route path="edit" element={<AnderzaakobjectzaakUpdate />} />
      <Route path="delete" element={<AnderzaakobjectzaakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AnderzaakobjectzaakRoutes;
