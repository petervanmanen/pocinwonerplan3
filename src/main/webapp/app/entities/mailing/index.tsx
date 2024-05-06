import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Mailing from './mailing';
import MailingDetail from './mailing-detail';
import MailingUpdate from './mailing-update';
import MailingDeleteDialog from './mailing-delete-dialog';

const MailingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Mailing />} />
    <Route path="new" element={<MailingUpdate />} />
    <Route path=":id">
      <Route index element={<MailingDetail />} />
      <Route path="edit" element={<MailingUpdate />} />
      <Route path="delete" element={<MailingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MailingRoutes;
